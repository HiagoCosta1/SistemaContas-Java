package com.hiago.contas.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProdutoDTO(
		Long id,
		
		@NotBlank(message = "Descrição é obrigatória")
		@Size(min = 3, max = 200, message = "Descrição deve ter entre 3 e 200 caracteres")
		String descricao,
		
		@NotNull(message = "Preço é obrigatório")
		@DecimalMin(value = "0.01", message = "Preço deve ser maior que zero")
		BigDecimal preco
		) {

}
