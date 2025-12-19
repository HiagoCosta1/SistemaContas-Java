package com.hiago.contas.dto;

import java.math.BigDecimal;

public record ProdutoDTO(
		Long id,
		String descricao,
		BigDecimal preco
		) {

}
