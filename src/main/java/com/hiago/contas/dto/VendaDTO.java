package com.hiago.contas.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.hiago.contas.domain.pagamento.MeioDePagamento;

public record VendaDTO(
		Long id,
		ClienteDTO cliente,
		List<ItemVendaDTO> itens,
		MeioDePagamento mdp,
		LocalDateTime dataHora,
		BigDecimal valorTotal
		) {

}
