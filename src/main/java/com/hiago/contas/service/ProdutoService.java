package com.hiago.contas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiago.contas.domain.Produto;
import com.hiago.contas.repository.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	

	public List<Produto> buscarTodos(){
		return produtoRepository.findAll();
	}
	
	public Optional<Produto> buscarPorId(Long id){
		return produtoRepository.findById(id);
	}
	
	public List<Produto> buscarPorDescricao(String descricao){
		return produtoRepository.findByDescricaoContainingIgnoreCase(descricao);
	}
	
	public Produto salvar (Produto produto) {
		return produtoRepository.save(produto);
	}
	
	public Produto atualizar(Long id, Produto produtoAtualizado) {
		Optional<Produto> ProdutoExistente = produtoRepository.findById(id);
		if(ProdutoExistente.isPresent()) {
			Produto produto = ProdutoExistente.get();
			produto.setDescricao(produtoAtualizado.getDescricao());
			produto.setPreco(produtoAtualizado.getPreco());
			return produtoRepository.save(produto);
		}
		throw new RuntimeException("Produto nao encontrado");
		
	}
	
	public void deletar(Long id) {
		if(!produtoRepository.existsById(id)) {
			throw new RuntimeException("Produto nao encontrado");
		}
		produtoRepository.deleteById(id);
		
	}
	
}
