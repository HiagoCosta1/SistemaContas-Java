package com.hiago.contas.domain.pagamento;

public class Avista extends MeioDePagamento {
	
	public Avista() {
		super("À Vista", StatusPagamento.PAGO);
	}
	
	@Override
    public String toString() {
        return "Status: " + getStatus().getDescricao();
 
	}
	
	@Override
	public void atualizarStatusPagamento() {}


}
