package com.hiago.contas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hiago.contas.domain.ItemVenda;
import com.hiago.contas.domain.PDV;

@Repository
public interface ItemVendaRepository extends JpaRepository<ItemVenda, Long> {
	
	List<ItemVenda> findByVenda(PDV venda);

}
