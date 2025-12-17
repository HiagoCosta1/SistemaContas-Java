package com.hiago.contas.domain.pagamento;

public class Fiado extends MeioDePagamento {
	public Fiado() {
		super("Fiado", StatusPagamento.PENDENTE);
	}
	
	@Override
    public String toString() {
        return "Status: " + getStatus().getDescricao();
	}
	
	@Override
	public void atualizarStatusPagamento() {
		setStatus(StatusPagamento.PAGO);
	}

}
