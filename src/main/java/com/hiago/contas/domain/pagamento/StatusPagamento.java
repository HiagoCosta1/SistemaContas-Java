package com.hiago.contas.domain.pagamento;

public enum StatusPagamento {
	
	PENDENTE(1, "Pagamento pendente"),
	PARCELADO(2, "Parcelado"),
	PAGO(3, "Pagamento Concluido");

	private final int codigo;
	private final String descricao;
	
	
	StatusPagamento(int codigo, String descricao){
		this.codigo = codigo;
		this.descricao = descricao;
	}


	public int getCodigo() {
		return codigo;
	}


	public String getDescricao() {
		return descricao;
	}
	
	public static StatusPagamento fromCodigo(int codigo) {
		for (StatusPagamento status : values()) {
			if(status.getCodigo() == codigo) {
				return status;
			}
		}
		throw new IllegalArgumentException("Codigo invalido: " + codigo);
	}
}
