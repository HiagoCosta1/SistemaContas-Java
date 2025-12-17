package com.hiago.contas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiago.contas.domain.Cliente;
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
	
	//salvar cliente
	public Cliente salvar(Cliente cliente) {
		return clienteRepository.save(cliente);
	}
	//atualizar o cliente
	public Cliente atualizar (Long id, Cliente clienteAtualizado) {
		Optional<Cliente> clienteExistente = clienteRepository.findById(id);	//Crio um Optional <Cliente> que pode ou não ter um cliente e me obriga a tratar caso não tenha nenhum objeto
		if(clienteExistente.isPresent()) {										//uso o metedo isPresent do Optional, para verificar se esse cliente existe mesmo
			Cliente cliente = clienteExistente.get();							//uso o metodo get do Optional para pegar os dados e colocar na variavel cliente
			cliente.setNome(clienteAtualizado.getNome());						//vou pegando os dados do clienteAtualizado com o get, e vou dando set para colocar nessa variavel cliente
			cliente.setEmail(clienteAtualizado.getEmail());
			cliente.setTelefone(clienteAtualizado.getTelefone());
			return clienteRepository.save(cliente);								//depois eu salvo no banco usando o .save e uso esse cliente que eu setei as informcaçÕes
		}
		throw new RuntimeException("Cliente nao encontrado");
	}
	
	//Deletar o cliente
	public void deletar(Long id) {
		if(!clienteRepository.existsById(id)) {
			throw new RuntimeException("Cliente nao encontrado!");
		}
		clienteRepository.deleteById(id);
	}
	
	
	public boolean existe(Long id) {
		return clienteRepository.existsById(id);
	}
	
}
