package com.hiago.contas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiago.contas.domain.Cliente;
import com.hiago.contas.exception.BusinessException;
import com.hiago.contas.exception.ResourceNotFoundException;
import com.hiago.contas.repository.ClienteRepository;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	// Buscar todos os clientes
	public List<Cliente> buscarTodos(){
		return clienteRepository.findAll();
	}
	
	//Buscar cliente por ID
	public Optional<Cliente> buscarPorId(Long id){
		return clienteRepository.findById(id);
	}
	
	public Cliente buscarPorIdOuLancarErro(Long id) {
		return clienteRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Cliente com ID" + id + "não encontrado"));
	}
	
	//salvar cliente
	public Cliente salvar(Cliente cliente) {
		if (cliente.getId() == null && clienteRepository.existsByEmail(cliente.getEmail())){
			throw new BusinessException("Ja existe um cliente cadastrado com o email: " + cliente.getEmail());
		}
		return clienteRepository.save(cliente);
	}
	
	//atualizar o cliente
	public Cliente atualizar (Long id, Cliente clienteAtualizado) {
		Cliente cliente = buscarPorIdOuLancarErro(id);
		
		if(!cliente.getEmail().equals(clienteAtualizado.getEmail()) && clienteRepository.existsByEmail(clienteAtualizado.getEmail())){
			throw new BusinessException("Já existe outro cliente com o email" + clienteAtualizado.getEmail());
		}
		
		cliente.setNome(clienteAtualizado.getNome());
		cliente.setEmail(clienteAtualizado.getEmail());
		cliente.setTelefone(clienteAtualizado.getTelefone());
		
		return clienteRepository.save(cliente);
	}
	
	//Deletar o cliente
	public void deletar(Long id) {
		
		Cliente cliente = buscarPorIdOuLancarErro(id);
		clienteRepository.delete(cliente);
	}
	
	
	public boolean existe(Long id) {
		return clienteRepository.existsById(id);
	}
	
}
