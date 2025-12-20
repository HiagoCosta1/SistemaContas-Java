package com.hiago.contas.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hiago.contas.domain.Usuario;
import com.hiago.contas.dto.UsuarioDTO;
import com.hiago.contas.mapper.UsuarioMapper;
import com.hiago.contas.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private UsuarioMapper usuarioMapper;

	@GetMapping
	public ResponseEntity<List<UsuarioDTO>> listarTodos() {
		List<Usuario> usuarios = usuarioService.buscarTodos();
		return ResponseEntity.ok(usuarioMapper.toDTOList(usuarios));
	}

	@GetMapping("/{id}")
	private ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable Long id) {
		return usuarioService.buscarPorId(id).map(usuarioMapper:: toDTO).map(ResponseEntity :: ok).orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	private ResponseEntity<UsuarioDTO> criar(@RequestBody UsuarioDTO usuarioDTO) {
		Usuario usuario = usuarioMapper.toEntity(usuarioDTO);
		Usuario novoUsuario = usuarioService.salvar(usuario);
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioMapper.toDTO(novoUsuario));
	}

	@PutMapping
	private ResponseEntity<UsuarioDTO> atualizar(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO) {
		return usuarioService.buscarPorId(id).map(UsuarioExistente -> {
			usuarioMapper.updateEntityFromDTO(usuarioDTO, UsuarioExistente);
			Usuario usuarioAtualizado = usuarioService.salvar(UsuarioExistente);
			return ResponseEntity.ok(usuarioMapper.toDTO(usuarioAtualizado));
		}).orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		return usuarioService.buscarPorId(id)
			.map(usuario -> {
				usuarioService.deletar(id);
				return ResponseEntity.noContent().<Void>build();
			})
			.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping("/login")
	public ResponseEntity<Usuario> login(@RequestBody LoginRequest loginRequest) {
		Optional<Usuario> usuario = usuarioService.login(loginRequest.getEmail(), loginRequest.getSenha());
		return usuario.map(ResponseEntity::ok).orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}

	public static class LoginRequest {
		private String email;
		private String senha;

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getSenha() {
			return senha;
		}

		public void setSenha(String senha) {
			this.senha = senha;
		}

	}

}
