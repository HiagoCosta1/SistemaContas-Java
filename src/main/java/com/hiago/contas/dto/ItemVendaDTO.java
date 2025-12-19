package com.hiago.contas.dto;

import java.math.BigDecimal;

public record ItemVendaDTO(
		Long id, 
		ProdutoDTO produto,
		Integer quantidade,
		BigDecimal precoUnitario,
		BigDecimal subtotal
		) {

}
