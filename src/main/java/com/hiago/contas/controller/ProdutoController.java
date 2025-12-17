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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hiago.contas.domain.Produto;
import com.hiago.contas.service.ProdutoService;

@RestController
@RequestMapping("/api/produtos")
@CrossOrigin(origins = "*")
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;
	
	@GetMapping
	public ResponseEntity<List<Produto>> listarTodos(){
		List<Produto> produtos = produtoService.buscarTodos();
		return ResponseEntity.ok(produtos);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Produto> buscarPorId(@PathVariable Long id){
		Optional<Produto> produto = produtoService.buscarPorId(id);
		return produto.map(ResponseEntity:: ok).orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/buscar")
    public ResponseEntity<List<Produto>> buscarPorDescricao(@RequestParam String descricao) {
        List<Produto> produtos = produtoService.buscarPorDescricao(descricao);
        return ResponseEntity.ok(produtos);
    }
	
	@PostMapping
	public ResponseEntity<Produto> criar(@RequestBody Produto produto){
		Produto novoProduto = produtoService.salvar(produto);
		return ResponseEntity.status(HttpStatus.CREATED).body(novoProduto);
	}
	
	@PutMapping
	public ResponseEntity<Produto> atualizar(@PathVariable Long id, @RequestBody Produto produto){
		try {
			Produto produtoAtualizado = produtoService.atualizar(id, produto);
			return ResponseEntity.ok(produtoAtualizado);
		}
		catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping
	public ResponseEntity<Produto> deletar(@PathVariable Long id){
		try {
			produtoService.deletar(id);
			return ResponseEntity.noContent().build();
		}
		catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
}
