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
import com.hiago.contas.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@GetMapping
	public ResponseEntity<List<Usuario>> listarTodos() {
		List<Usuario> usuarios = usuarioService.buscarTodos();
		return ResponseEntity.ok(usuarios);
	}

	@GetMapping("/{id}")
	private ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
		Optional<Usuario> usuario = usuarioService.buscarPorId(id);
		return usuario.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	private ResponseEntity<Usuario> criar(@RequestBody Usuario usuario) {
		try {
			Usuario novoUsuario = usuarioService.salvar(usuario);
			return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@PutMapping
	private ResponseEntity<Usuario> atualizar(@PathVariable Long id, @RequestBody Usuario usuario) {
		try {
			Usuario usuarioAtualizado = usuarioService.atualizar(id, usuario);
			return ResponseEntity.ok(usuarioAtualizado);
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping
	public ResponseEntity<Usuario> deletar(@PathVariable Long id) {
		try {
			usuarioService.deletar(id);
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
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
