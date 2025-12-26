package com.hiago.contas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NovaVendaRequest(
		
		@NotNull(message = "ID do cliente é obrigatório")
		Long clienteId,
		
		@NotBlank(message = "Meio de pagamento é obrigatório")
		String mdp
		) {

}
