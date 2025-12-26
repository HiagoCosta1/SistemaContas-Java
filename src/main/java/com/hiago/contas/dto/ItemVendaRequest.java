package com.hiago.contas.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ItemVendaRequest(
		
		@NotNull(message = "ID do produto é obrigatório")
		Long produtoID,
		
		@NotNull(message = "Quantidade é obrigatória")
		@Min(value = 1, message = "Quantidade deve ser no mínimo 1")
		Integer quantidade) {

}
