package com.hiago.contas.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ClienteDTO(
		Long id, 
		
		@NotBlank(message = "Nome é obrigatório")
		@Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
		String nome,
		

		@NotBlank(message = "Email é obrigatório")
		@Email(message = "Email inválido")
		@Size(max = 100, message = "Email deve ter no máximo 100 caracteres")
		String email,
		
		@Pattern(regexp = "\\(\\d{2}\\) \\d{4,5}-\\d{4}", message = "Telefone inválido. Use o formato: (11) 99999-9999")
		String telefone
		) {

}
