package com.hiago.contas.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioDTO(
		
		Long id,
		
		@NotBlank(message = "Nome é obrigatório")
		@Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
		String nome,
		
		@NotBlank(message = "Email é obrigatório")
		@Email(message = "Email inválido")
		@Size(max = 100, message = "Email deve ter no máximo 100 caracteres")
		String email,
		
		@Size(min = 6, max = 255, message = "Senha deve ter entre 6 e 255 caracteres")
		String senha
		) {

}
