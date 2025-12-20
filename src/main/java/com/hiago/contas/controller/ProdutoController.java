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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hiago.contas.domain.Produto;
import com.hiago.contas.dto.ProdutoDTO;
import com.hiago.contas.mapper.ProdutoMapper;
import com.hiago.contas.service.ProdutoService;

@RestController
@RequestMapping("/api/produtos")
@CrossOrigin(origins = "*")
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private ProdutoMapper produtoMapper;

	@GetMapping
	public ResponseEntity<List<ProdutoDTO>> listarTodos() {
		List<Produto> produtos = produtoService.buscarTodos();
		return ResponseEntity.ok(produtoMapper.toDTOList(produtos));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProdutoDTO> buscarPorId(@PathVariable Long id) {
		return produtoService.buscarPorId(id).map(produtoMapper::toDTO).map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/buscar")
	public ResponseEntity<List<ProdutoDTO>> buscarPorDescricao(@RequestParam String descricao) {
		List<Produto> produtos = produtoService.buscarPorDescricao(descricao);
		return ResponseEntity.ok(produtoMapper.toDTOList(produtos));
	}

	@PostMapping
	public ResponseEntity<ProdutoDTO> criar(@RequestBody ProdutoDTO produtoDTO) {
		Produto produto = produtoMapper.toEntity(produtoDTO);
		Produto novoProduto = produtoService.salvar(produto);
		return ResponseEntity.status(HttpStatus.CREATED).body(produtoMapper.toDTO(novoProduto));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ProdutoDTO> atualizar(@PathVariable Long id, @RequestBody ProdutoDTO produtoDTO) {
		return produtoService.buscarPorId(id).map(produtoExistente -> {
			produtoMapper.updateEntityFromDTO(produtoDTO, produtoExistente);
			Produto produtoAtualizado = produtoService.salvar(produtoExistente);
			return ResponseEntity.ok(produtoMapper.toDTO(produtoAtualizado));
		}).orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		return produtoService.buscarPorId(id).map(produto -> {
			produtoService.deletar(id);
			return ResponseEntity.noContent().<Void>build();
		}).orElse(ResponseEntity.notFound().build());
	}
}
