package com.hiago.contas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hiago.contas.domain.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

	List<Produto> findByDescricaoContainingIgnoreCase(String descricao);
	boolean existsByDescricaoIgnoreCase(String descricao);
}
