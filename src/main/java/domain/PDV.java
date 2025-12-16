package domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name = "venda")
public class PDV {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "cliente_id", nullable = false)
	private Cliente cliente;
	
	@OneToMany (mappedBy = "venda", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ItemVenda> itens = new ArrayList<>();
	
	@Column(name = "meio_pagamento", length = 50)
	private String meioPagamento;
	
	@Column(name = "data_hora", nullable = false)
	private LocalDateTime dataHora;
	
	@Column(name = "valor_total", nullable = false, precision = 10, scale = 2)
	private BigDecimal valorTotal;
	

	public PDV() {
		this.dataHora = LocalDateTime.now();
		this.valorTotal = BigDecimal.ZERO;
	}


	public PDV(Cliente cliente, String meioPagamento) {
		this.cliente = cliente;
		this.itens = new ArrayList<>();
		this.meioPagamento = meioPagamento;
		this.dataHora = LocalDateTime.now();
		this.valorTotal = BigDecimal.ZERO;
	}
	
	public void adicionarProduto(Produto produto, Integer quantidade) {
		ItemVenda item = new ItemVenda(this, produto, quantidade, produto.getPreco()); 
		this.itens.add(item);
		calcularTotal();
	}
	
	public void calcularTotal() {
		this.valorTotal = itens.stream().map(ItemVenda::getSubtotal).reduce(BigDecimal.ZERO, BigDecimal :: add);
	}


	

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Cliente getCliente() {
		return cliente;
	}


	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}


	public List<ItemVenda> getItens() {
		return itens;
	}


	public void setItens(List<ItemVenda> itens) {
		this.itens = itens;
	}


	public String getMeioPagamento() {
		return meioPagamento;
	}


	public void setMeioPagamento(String meioPagamento) {
		this.meioPagamento = meioPagamento;
	}


	public LocalDateTime getDataHora() {
		return dataHora;
	}


	public void setDataHora(LocalDateTime dataHora) {
		this.dataHora = dataHora;
	}


	public BigDecimal getValorTotal() {
		return valorTotal;
	}


	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}


	@Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        StringBuilder listaProdutos = new StringBuilder();
        
        for (ItemVenda item : itens) {
            listaProdutos.append(item.getProduto().getDescricao())
                         .append(" - qtd: ")
                         .append(item.getQuantidade())
                         .append(", ");
        }
        
        return "PDV {" +
                "id='" + id + '\'' +
                ", cliente=" + (cliente != null ? cliente.getNome() : "N/A") +
                ", produtos=" + listaProdutos +
                ", total=R$ " + valorTotal +
                ", data=" + dataHora.format(formatter) +
                ", meioDePagamento=" + meioPagamento +
                '}';
    }


	@Override
	public int hashCode() {
		return Objects.hash(id);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PDV other = (PDV) obj;
		return Objects.equals(id, other.id);
	}
	
	
	
	
}
