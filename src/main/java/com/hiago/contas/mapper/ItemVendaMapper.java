package com.hiago.contas.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.hiago.contas.domain.ItemVenda;
import com.hiago.contas.dto.ItemVendaDTO;

@Mapper(componentModel = "spring", uses = {ProdutoMapper.class})
public interface ItemVendaMapper {
	
	
	ItemVendaDTO toDTO(ItemVenda itemVenda);

	List<ItemVendaDTO> toDTOList(List<ItemVenda> itens);
}
