package com.hiago.contas.domain.pagamento;

public abstract class  MeioDePagamento {

	protected String tipo;
	protected StatusPagamento status;
	
	
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
	
	public abstract void atualizarStatusPagamento();
}


