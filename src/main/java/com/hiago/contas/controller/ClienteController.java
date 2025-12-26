package com.hiago.contas.controller;

import java.util.List;

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

import com.hiago.contas.domain.Cliente;
import com.hiago.contas.dto.ClienteDTO;
import com.hiago.contas.mapper.ClienteMapper;
import com.hiago.contas.service.ClienteService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private ClienteMapper clienteMapper;
	
	@GetMapping
	public ResponseEntity<List<ClienteDTO>> listarTodos(){
		List<Cliente> clientes = clienteService.buscarTodos();
		return ResponseEntity.ok(clienteMapper.toDTOList(clientes));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ClienteDTO> buscarPorId(@PathVariable Long id){
		return clienteService.buscarPorId(id).map(clienteMapper :: toDTO).map(ResponseEntity:: ok).orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping
	public ResponseEntity<ClienteDTO> criar(@Valid @RequestBody ClienteDTO clienteDTO){
		Cliente cliente = clienteMapper.toEntity(clienteDTO);
		Cliente novoCliente = clienteService.salvar(cliente);
		return ResponseEntity.status(HttpStatus.CREATED).body(clienteMapper.toDTO(novoCliente));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ClienteDTO> atualizar(@PathVariable Long id,@Valid @RequestBody ClienteDTO clienteDTO) {
		return clienteService.buscarPorId(id)
			.map(clienteExistente -> {
				clienteMapper.updateEntityFromDTO(clienteDTO, clienteExistente);
				Cliente clienteAtualizado = clienteService.salvar(clienteExistente);
				return ResponseEntity.ok(clienteMapper.toDTO(clienteAtualizado));
			})
			.orElse(ResponseEntity.notFound().build());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		return clienteService.buscarPorId(id)
			.map(cliente -> {
				clienteService.deletar(id);
				return ResponseEntity.noContent().<Void>build();
			})
			.orElse(ResponseEntity.notFound().build());
	}

}
