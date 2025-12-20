package com.hiago.contas.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.hiago.contas.domain.Produto;
import com.hiago.contas.dto.ProdutoDTO;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {
	
	ProdutoDTO toDTO(Produto produto);
	
	List<ProdutoDTO> toDTOList(List<Produto> produtos);
	
	Produto toEntity(ProdutoDTO dto);

	void updateEntityFromDTO(ProdutoDTO dto, @MappingTarget Produto produto);
}
