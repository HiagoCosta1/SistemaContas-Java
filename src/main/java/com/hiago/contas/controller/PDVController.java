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
import org.springframework.web.bind.annotation.RestController;

import com.hiago.contas.domain.Cliente;
import com.hiago.contas.domain.PDV;
import com.hiago.contas.domain.Produto;
import com.hiago.contas.domain.pagamento.MeioDePagamento;
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
	
	@GetMapping
	public ResponseEntity<List<PDV>> listarTodos(){
		List<PDV> vendas = pdvService.buscarTodos();
		return ResponseEntity.ok(vendas);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PDV> buscarPorId(@PathVariable Long id){
		Optional<PDV> venda = pdvService.buscarPorId(id);
		return venda.map(ResponseEntity :: ok).orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<PDV>> buscarPorCliente(@PathVariable Long clienteId) {
        Optional<Cliente> cliente = clienteService.buscarPorId(clienteId);
        if (cliente.isPresent()) {
            List<PDV> vendas = pdvService.buscarPorCliente(cliente.get());
            return ResponseEntity.ok(vendas);
        }
        return ResponseEntity.notFound().build();
    }
	
	@PostMapping
	public ResponseEntity<PDV> criarVenda(@RequestBody NovaVendaRequest request){
		Optional<Cliente> cliente = clienteService.buscarPorId(request.getClienteId());
		if(cliente.isPresent()) {
			PDV venda = pdvService.criarVenda(cliente.get(), request.getMdp());
			return ResponseEntity.status(HttpStatus.CREATED).body(venda);
		}
		return ResponseEntity.badRequest().build();
	}
	
	public ResponseEntity<PDV> adicionarProduto(@PathVariable Long id, @RequestBody ItemVendaRequest request){
		try {
			Optional<Produto> produto = produtoService.buscarPorId(request.getProdutoID());
			if(produto.isPresent()) {
				PDV venda = pdvService.adicionarProduto(id, produto.get(), request.getQuantidade());
				return ResponseEntity.ok(venda);
			}
			return ResponseEntity.badRequest().build();
		}
		catch(Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PutMapping("/{id}/finalizar")
	public ResponseEntity<PDV> finalizarVenda(@PathVariable Long id){
		try {
			PDV venda = pdvService.finalizarVenda(id);
			return ResponseEntity.ok(venda);
		}
		catch(Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<PDV> deletar (@PathVariable Long id){
		try {
			pdvService.deletar(id);
			return ResponseEntity.noContent().build();
		}
		catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
		
	}
	
	
	public static class NovaVendaRequest{
		private Long clienteId;
		private MeioDePagamento mdp;
		public Long getClienteId() {
			return clienteId;
		}
		public void setClienteId(Long clienteId) {
			this.clienteId = clienteId;
		}
		public MeioDePagamento getMdp() {
			return mdp;
		}
		public void setMdp(MeioDePagamento mdp) {
			this.mdp = mdp;
		}

	}
	
	public static class ItemVendaRequest{
		private Long produtoID;
		private Integer quantidade;
		public Long getProdutoID() {
			return produtoID;
		}
		public void setProdutoID(Long produtoID) {
			this.produtoID = produtoID;
		}
		public Integer getQuantidade() {
			return quantidade;
		}
		public void setQuantidade(Integer quantidade) {
			this.quantidade = quantidade;
		}
		
		
	}
	
	
	
}
