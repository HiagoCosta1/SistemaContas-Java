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
import org.springframework.web.bind.annotation.RestController;

import com.hiago.contas.domain.PDV;
import com.hiago.contas.domain.pagamento.Avista;
import com.hiago.contas.domain.pagamento.Fiado;
import com.hiago.contas.domain.pagamento.MeioDePagamento;
import com.hiago.contas.dto.ItemVendaRequest;
import com.hiago.contas.dto.NovaVendaRequest;
import com.hiago.contas.dto.VendaDTO;
import com.hiago.contas.mapper.VendaMapper;
import com.hiago.contas.service.ClienteService;
import com.hiago.contas.service.PDVService;
import com.hiago.contas.service.ProdutoService;

@RestController
@RequestMapping("/api/vendas")
@CrossOrigin(origins = "*")
public class PDVController {

	@Autowired
	private PDVService pdvService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private VendaMapper vendaMapper;
	
	@GetMapping
	public ResponseEntity<List<VendaDTO>> listarTodos() {
		List<PDV> vendas = pdvService.buscarTodos();
		return ResponseEntity.ok(vendaMapper.toDTOList(vendas));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<VendaDTO> buscarPorId(@PathVariable Long id) {
		return pdvService.buscarPorId(id)
			.map(vendaMapper::toDTO)
			.map(ResponseEntity::ok)
			.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/cliente/{clienteId}")
	public ResponseEntity<List<VendaDTO>> buscarPorCliente(@PathVariable Long clienteId) {
		return clienteService.buscarPorId(clienteId)
			.map(cliente -> {
				List<PDV> vendas = pdvService.buscarPorCliente(cliente);
				return ResponseEntity.ok(vendaMapper.toDTOList(vendas));
			})
			.orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping
	public ResponseEntity<VendaDTO> criarVenda(@RequestBody NovaVendaRequest request) {
		return clienteService.buscarPorId(request.clienteId())
			.map(cliente -> {
				MeioDePagamento mdp = criarMeioDePagamento(request.mdp());
				PDV venda = pdvService.criarVenda(cliente, mdp);
				return ResponseEntity.status(HttpStatus.CREATED).body(vendaMapper.toDTO(venda));
			})
			.orElse(ResponseEntity.badRequest().build());
	}
	
	@PostMapping("/{id}/itens")
	public ResponseEntity<VendaDTO> adicionarProduto(@PathVariable Long id, @RequestBody ItemVendaRequest request) {
		try {
			return produtoService.buscarPorId(request.produtoID())
				.map(produto -> {
					PDV venda = pdvService.adicionarProduto(id, produto, request.quantidade());
					return ResponseEntity.ok(vendaMapper.toDTO(venda));
				})
				.orElse(ResponseEntity.badRequest().build());
		} catch(Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PutMapping("/{id}/finalizar")
	public ResponseEntity<VendaDTO> finalizarVenda(@PathVariable Long id) {
		try {
			PDV venda = pdvService.finalizarVenda(id);
			return ResponseEntity.ok(vendaMapper.toDTO(venda));
		} catch(Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PutMapping("/{id}/pagamento")
	public ResponseEntity<VendaDTO> atualizarPagamento(@PathVariable Long id) {
		try {
			PDV venda = pdvService.buscarPorId(id)
				.orElseThrow(() -> new RuntimeException("Venda não encontrada"));
			
			venda.getMdp().atualizarStatusPagamento();
			PDV vendaAtualizada = pdvService.salvar(venda);
			
			return ResponseEntity.ok(vendaMapper.toDTO(vendaAtualizada));
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		try {
			pdvService.deletar(id);
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	// Método auxiliar privado
	private MeioDePagamento criarMeioDePagamento(String tipo) {
		if (tipo.equalsIgnoreCase("AVISTA") || tipo.equalsIgnoreCase("À VISTA")) {
			return new Avista();
		} else if (tipo.equalsIgnoreCase("FIADO")) {
			return new Fiado();
		}
		return new Avista(); // Padrão
	}
}