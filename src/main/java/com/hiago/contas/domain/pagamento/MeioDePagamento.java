package com.hiago.contas.domain.pagamento;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public class MeioDePagamento {

	@Column(name = "tipo_pagamento", length = 50)
	private String tipo;
	
	@Enumerated(EnumType.STRING)
    @Column(name = "status_pagamento")
	private StatusPagamento status;
	
	
	public MeioDePagamento() {
		
	}

	public MeioDePagamento(String tipo, StatusPagamento status) {
		super();
		this.tipo = tipo;
		this.status = status;
	}


	public String getTipo() {
		return tipo;
	}
	


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public StatusPagamento getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = StatusPagamento.fromCodigo(status);
	}
	
	public void setStatus(StatusPagamento status) {
		this.status = status;
	}
	
	@Override
    public String toString() {
        return "MeioDePagamento {" +
                "tipo='" + tipo + '\'' +
                ", status='" + status.getDescricao() + '\'' +
                '}';
	}
	
	public void atualizarStatusPagamento() {
		
	}
}


