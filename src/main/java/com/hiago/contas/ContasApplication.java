package com.hiago.contas;

import java.math.BigDecimal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.hiago.contas.domain.Cliente;
import com.hiago.contas.domain.ItemVenda;
import com.hiago.contas.domain.PDV;
import com.hiago.contas.domain.Produto;
import com.hiago.contas.domain.pagamento.Fiado;
import com.hiago.contas.domain.pagamento.MeioDePagamento;

@SpringBootApplication
public class ContasApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContasApplication.class, args);
		
		Cliente h1 = new Cliente("hiago", "hiago@gmail.com", "13997456716");
		Produto p1 = new Produto("Notebook",  new BigDecimal("1000.00"));
		MeioDePagamento fiado = new Fiado();
		PDV venda = new PDV(h1, fiado);
		ItemVenda item = new ItemVenda(venda, p1, 2, new BigDecimal(1010.00));
		
		System.out.println(venda);
	}

}
