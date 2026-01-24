package com.hiago.contas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hiago.contas.domain.ItemVenda;
import com.hiago.contas.dto.ItemVendaDTO;
import com.hiago.contas.mapper.ItemVendaMapper;
import com.hiago.contas.service.ItemVendaService;

@RestController
@RequestMapping("/api/itens-venda")
@CrossOrigin(origins = "*")
public class ItemVendaController {
	
	@Autowired
    private ItemVendaService itemVendaService;
	
	@Autowired
	private ItemVendaMapper itemVendaMapper;
	
	@GetMapping
    public ResponseEntity<List<ItemVendaDTO>> listarTodos() {
        List<ItemVenda> itens = itemVendaService.buscarTodos();
        return ResponseEntity.ok(itemVendaMapper.toDTOList(itens));
    }
	
	@GetMapping("/{id}")
    public ResponseEntity<ItemVendaDTO> buscarPorId(@PathVariable Long id) {
       return itemVendaService.buscarPorId(id).map(itemVendaMapper:: toDTO).map(ResponseEntity:: ok).orElse(ResponseEntity.notFound().build());
    }
	
	@DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
       return itemVendaService.buscarPorId(id).map(item -> {
    	   itemVendaService.deletar(id);
    	   return ResponseEntity.noContent().<Void>build();
       }) .orElse(ResponseEntity.notFound().build());
    }

}
