package com.hiago.contas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hiago.contas.domain.Cliente;
import com.hiago.contas.domain.Usuario;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>  {
	
	Optional<Usuario> findByEmail(String email);
	boolean existsByEmail (String email);
	
}
