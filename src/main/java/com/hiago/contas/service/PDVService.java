package com.hiago.contas.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiago.contas.domain.Cliente;
import com.hiago.contas.domain.PDV;
import com.hiago.contas.domain.Produto;
import com.hiago.contas.domain.pagamento.MeioDePagamento;
import com.hiago.contas.exception.BusinessException;
import com.hiago.contas.exception.ResourceNotFoundException;
import com.hiago.contas.repository.PDVRepository;

@Service
public class PDVService {

	@Autowired
	private PDVRepository pdvRepository;

	public List<PDV> buscarTodos() {
		return pdvRepository.findAll();
	}

	public Optional<PDV> buscarPorId(Long id) {
		return pdvRepository.findById(id);
	}

	public PDV buscarPorIdOuLancarErro(Long id) {
		return pdvRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Venda com ID " + id + " não encontrada"));
	}

	public List<PDV> buscarPorCliente(Cliente cliente) {
		if (cliente == null) {
			throw new BusinessException("Cliente não pode ser nulo");
		}
		return pdvRepository.findByCliente(cliente);
	}

	public List<PDV> buscaPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
		if (inicio == null || fim == null) {
			throw new BusinessException("Data de inicio e fim são obrigatórias");
		}

		if (inicio.isAfter(fim)) {
			throw new BusinessException("Data de inicio não pode ser posterior à data de fim");
		}

		return pdvRepository.findByDataHoraBetween(inicio, fim);
	}

	public PDV criarVenda(Cliente cliente, MeioDePagamento mdp) {
		if (cliente == null) {
			throw new BusinessException("CLiente é obrigatório para criar uma venda");
		}

		if (mdp == null) {
			throw new BusinessException("Meio de pagamento é obrigatório");
		}

		PDV venda = new PDV(cliente, mdp);
		return pdvRepository.save(venda);
	}

	public PDV salvar(PDV venda) {
		if (venda == null) {
			throw new BusinessException("Venda não pode ser nula");
		}
		return pdvRepository.save(venda);
	}

	public PDV adicionarProduto(Long vendaId, Produto produto, Integer quantidade) {
		PDV venda = buscarPorIdOuLancarErro(vendaId);

		if (produto == null) {
			throw new BusinessException("Produto não pode ser nulo");
		}

		if (quantidade == null || quantidade <= 0) {
			throw new BusinessException("Quantidade deve ser maior que zero");
		}

		venda.adicionarProduto(produto, quantidade);
		return pdvRepository.save(venda);
	}

	public PDV finalizarVenda(Long vendaId) {
		PDV venda = buscarPorIdOuLancarErro(vendaId);

		if (venda.getItens() == null || venda.getItens().isEmpty()) {
			throw new BusinessException("Não é possivel finalizar uma venda sem itens");
		}

		venda.calcularTotal();
		return pdvRepository.save(venda);
	}

	public void deletar(Long id) {
		PDV venda = buscarPorIdOuLancarErro(id);
		pdvRepository.delete(venda);

	}
}
