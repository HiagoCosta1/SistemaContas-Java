package com.hiago.contas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiago.contas.domain.ItemVenda;
import com.hiago.contas.domain.PDV;
import com.hiago.contas.repository.ItemVendaRepository;

@Service
public class ItemVendaService {
	
	@Autowired
	private ItemVendaRepository itemVendaRepository;
	
	public List<ItemVenda> buscarTodos() {
		return itemVendaRepository.findAll();
	}
	
	public Optional<ItemVenda> buscarPorId(Long id){
		return itemVendaRepository.findById(id);
	}
	
	 public List<ItemVenda> buscarPorVenda(PDV venda) {
	    return itemVendaRepository.findByVenda(venda);
	}
	 
	public ItemVenda salvar (ItemVenda itemVenda) {
		return itemVendaRepository.save(itemVenda);
	}
	
	public void deletar (Long id) {
		if(!itemVendaRepository.existsById(id)) {
			throw new RuntimeException("Item nao encontrado");
		}
		itemVendaRepository.deleteById(id);
	}

}
