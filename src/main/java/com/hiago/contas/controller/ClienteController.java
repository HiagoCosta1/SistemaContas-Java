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

import com.hiago.contas.domain.Cliente;
import com.hiago.contas.service.ClienteService;


@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {
	
	@Autowired
	private ClienteService clienteService;
	
	@GetMapping
	public ResponseEntity<List<Cliente>> listarTodos(){
		List<Cliente> clientes = clienteService.buscarTodos();
		return ResponseEntity.ok(clientes);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Cliente> buscarPorId(@PathVariable Long id){
		Optional<Cliente> cliente = clienteService.buscarPorId(id);
		return cliente.map(ResponseEntity:: ok).orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping
	public ResponseEntity<Cliente> criar(@RequestBody Cliente cliente){
		try {
			Cliente novoCliente = clienteService.salvar(cliente);
			return ResponseEntity.status(HttpStatus.CREATED).body(novoCliente);
		}
		catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Cliente> atualizar(@PathVariable Long id, @RequestBody Cliente cliente){
		try {
			Cliente clienteAtualizado = clienteService.atualizar(id, cliente);
			return ResponseEntity.ok(clienteAtualizado);
		}
		catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Cliente> deletar(@PathVariable Long id){
		try {
			clienteService.deletar(id);
			return ResponseEntity.noContent().build();
		}
		catch(Exception e) {
			return ResponseEntity.notFound().build();
		}
	}

}
