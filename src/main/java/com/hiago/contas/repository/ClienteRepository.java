package com.hiago.contas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hiago.contas.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>  {
	
	Cliente findByEmail(String email);
	boolean existsByEmail (String email);
	
}
