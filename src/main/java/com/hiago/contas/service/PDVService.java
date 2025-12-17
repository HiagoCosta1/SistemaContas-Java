package com.hiago.contas.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiago.contas.domain.Cliente;
import com.hiago.contas.domain.PDV;
import com.hiago.contas.domain.Produto;
import com.hiago.contas.domain.pagamento.MeioDePagamento;
import com.hiago.contas.repository.PDVRepository;

@Service
public class PDVService {
	
	@Autowired
	private PDVRepository pdvRepository;
	
	public List<PDV> buscarTodos(){
		return pdvRepository.findAll();
	}
	
	public Optional<PDV> buscarPorId(Long id){
		return pdvRepository.findById(id);
	}
	
	public List<PDV> buscarPorCliente(Cliente cliente){
		return pdvRepository.findByCliente(cliente);
	}
	
	public List<PDV> buscaPorPeriodo(LocalDateTime inicio, LocalDateTime fim){
		return pdvRepository.findByDataHoraBetween(inicio, fim);
	}
	
	public PDV criarVenda(Cliente cliente, MeioDePagamento mdp) {					
		PDV venda = new PDV(cliente, mdp);													//instancio uma nova venda
		return pdvRepository.save(venda);													// salvo ela no banco
	}
	
	public PDV adicionarProduto(Long vendaId, Produto produto, Integer quantidade) {
		Optional<PDV> vendaOpt = pdvRepository.findById(vendaId);							//busco pelo id no banco	
		if(vendaOpt.isPresent()) {															//verifico se ele de fato existe
			PDV venda = vendaOpt.get();														//pego as informações e adiciono em uma variavel venda	
			venda.adicionarProduto(produto, quantidade);									//uso o metodo adicionarProduto para colocar o produto e quantidade na venda
			return pdvRepository.save(venda);		
		}
		throw new RuntimeException("Venda nao encontrada!");
	}
	
	
	public PDV finalizarVenda(Long vendaId) {
		Optional<PDV> vendaOpt = pdvRepository.findById(vendaId);
		if(vendaOpt.isPresent()) {	
			PDV venda = vendaOpt.get();														
			venda.calcularTotal();															//faço o processo de buscar a venda pelo id e depois uso o metodo calcularTotal para salvar na venda
			return pdvRepository.save(venda);
		}
		throw new RuntimeException("Venda nao encontrada!");
	}
	
	public void deletar(Long id) {
		if(!pdvRepository.existsById(id)) {
			throw new RuntimeException("Venda nao encontrada!");
		}
		pdvRepository.deleteById(id);
	}

}
