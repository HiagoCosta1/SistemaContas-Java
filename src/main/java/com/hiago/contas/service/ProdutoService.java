package com.hiago.contas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiago.contas.domain.Produto;
import com.hiago.contas.exception.BusinessException;
import com.hiago.contas.exception.ResourceNotFoundException;
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
	
	public Produto buscarPorIdOuLancarErro(Long id) {
		return produtoRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Produto com ID" + id + "não encontrado"));
	}
	
	public List<Produto> buscarPorDescricao(String descricao){
		return produtoRepository.findByDescricaoContainingIgnoreCase(descricao);
	}
	
	public Produto salvar (Produto produto) {
		if(produto.getId() == null && produtoRepository.existsByDescricaoIgnoreCase(produto.getDescricao())) {
			throw new BusinessException("Já existe um produto com a descrição: " + produto.getDescricao());
		}
		return produtoRepository.save(produto);
	}
	
	public Produto atualizar(Long id, Produto produto) {
		Produto produtoExistente = buscarPorIdOuLancarErro(id);
		
		if (!produtoExistente.getDescricao().equalsIgnoreCase(produto.getDescricao()) && produtoRepository.existsByDescricaoIgnoreCase(produto.getDescricao())) {
			throw new BusinessException("Já existe outro produto com essa descrição: " + produto.getDescricao());
		}
		
		produtoExistente.setDescricao(produto.getDescricao());
		produtoExistente.setPreco(produto.getPreco());
		
		return produtoRepository.save(produtoExistente);
	}
	
	public void deletar(Long id) {
		Produto produto = buscarPorIdOuLancarErro(id);
		produtoRepository.delete(produto);
	}
	
}
