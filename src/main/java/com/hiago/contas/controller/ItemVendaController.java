package com.hiago.contas.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hiago.contas.domain.ItemVenda;
import com.hiago.contas.service.ItemVendaService;

@RestController
@RequestMapping("/api/itens-venda")
@CrossOrigin(origins = "*")
public class ItemVendaController {
	
	@Autowired
    private ItemVendaService itemVendaService;
	
	@GetMapping
    public ResponseEntity<List<ItemVenda>> listarTodos() {
        List<ItemVenda> itens = itemVendaService.buscarTodos();
        return ResponseEntity.ok(itens);
    }
	
	@GetMapping("/{id}")
    public ResponseEntity<ItemVenda> buscarPorId(@PathVariable Long id) {
        Optional<ItemVenda> item = itemVendaService.buscarPorId(id);
        return item.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }
	
	@DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            itemVendaService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
