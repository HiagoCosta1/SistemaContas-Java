package com.hiago.contas.domain;

import java.math.BigDecimal;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "item_venda")
public class ItemVenda {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "venda_id", nullable = false)
	private PDV venda;
	
	@ManyToOne
	@JoinColumn(name = "produto_id", nullable = false)
	private Produto produto;
	
	@Column(name = "quantidade", nullable = false)
	private Integer quantidade;
	
	@Column(name = "preco_unitario", nullable = false, precision = 10, scale = 2)
	private BigDecimal precoUnitario;
	
	@Column(name = "subtotal", nullable = false, precision = 10, scale = 2)
	private BigDecimal subtotal;
	
	public ItemVenda() {
		
	}

	public ItemVenda(PDV venda, Produto produto, Integer quantidade, BigDecimal precoUnitario) {
		super();
		this.venda = venda;
		this.produto = produto;
		this.quantidade = quantidade;
		this.precoUnitario = precoUnitario;
		this.subtotal = precoUnitario.multiply(new BigDecimal(quantidade));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PDV getVenda() {
		return venda;
	}

	public void setVenda(PDV venda) {
		this.venda = venda;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
		if(this.precoUnitario != null) {
			this.subtotal = this.precoUnitario.multiply(new BigDecimal(quantidade));
		}
	}

	public BigDecimal getPrecoUnitario() {
		return precoUnitario;
	}

	public void setPrecoUnitario(BigDecimal precoUnitario) {
		this.precoUnitario = precoUnitario;
		if(this.quantidade != null) {
			this.subtotal = precoUnitario.multiply(new BigDecimal(this.quantidade));
		}
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
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
		ItemVenda other = (ItemVenda) obj;
		return Objects.equals(id, other.id);
	}
	
	
	
	
	

}
