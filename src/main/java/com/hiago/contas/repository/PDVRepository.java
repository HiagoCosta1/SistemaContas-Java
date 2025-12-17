package com.hiago.contas.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hiago.contas.domain.Cliente;
import com.hiago.contas.domain.PDV;

@Repository 
public interface PDVRepository extends JpaRepository<PDV, Long> {

	List<PDV> findByCliente(Cliente cliente);
    List<PDV> findByDataHoraBetween(LocalDateTime inicio, LocalDateTime fim);
    List<PDV> findByClienteAndDataHoraBetween(Cliente cliente, LocalDateTime inicio, LocalDateTime fim);
}
