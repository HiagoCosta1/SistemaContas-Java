package com.hiago.contas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiago.contas.domain.ItemVenda;
import com.hiago.contas.domain.PDV;
import com.hiago.contas.exception.BusinessException;
import com.hiago.contas.exception.ResourceNotFoundException;
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
	
	public ItemVenda buscarPorIdOuLancarErro(Long id) {
		return itemVendaRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Item de venda com ID " + id + " não encontrado"));
	}
	
	 public List<ItemVenda> buscarPorVenda(PDV venda) {
		 if (venda == null) {
				throw new BusinessException("Venda não pode ser nula");
			}
			return itemVendaRepository.findByVenda(venda);
	}
	 
	public ItemVenda salvar (ItemVenda itemVenda) {
		if (itemVenda == null) {
			throw new BusinessException("Item de venda não pode ser nulo");
		}
		
		if (itemVenda.getProduto() == null) {
			throw new BusinessException("Produto é obrigatório no item de venda");
		}
		
		if (itemVenda.getQuantidade() == null || itemVenda.getQuantidade() <= 0) {
			throw new BusinessException("Quantidade deve ser maior que zero");
		}
		
		if (itemVenda.getPrecoUnitario() == null || 
		    itemVenda.getPrecoUnitario().compareTo(java.math.BigDecimal.ZERO) <= 0) {
			throw new BusinessException("Preço unitário deve ser maior que zero");
		}
		
		return itemVendaRepository.save(itemVenda);
	}
	
	public void deletar (Long id) {
		ItemVenda item = buscarPorIdOuLancarErro(id);
		itemVendaRepository.delete(item);
	}

}
