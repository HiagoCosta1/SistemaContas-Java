package com.hiago.contas.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.hiago.contas.domain.PDV;
import com.hiago.contas.dto.VendaDTO;

@Mapper(componentModel = "spring", uses = {ClienteMapper.class, ItemVendaMapper.class})
public interface VendaMapper {
	
	
	VendaDTO toDTO(PDV venda);
	
	List<VendaDTO> toDTOList(List<PDV> vendas);
	
	

}
