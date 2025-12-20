package com.hiago.contas.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.hiago.contas.domain.Cliente;
import com.hiago.contas.dto.ClienteDTO;

@Mapper(componentModel = "spring")
public interface  ClienteMapper {
	
	
	// Entity → DTO
	ClienteDTO toDTO (Cliente cliente);
	
	List<ClienteDTO> toDTOList(List<Cliente> clientes);
	
	 // DTO → Entity
	Cliente toEntity (ClienteDTO dto);
	
	
	//Atualizar Entity existente com dados do dto (Ignora ID)
	@Mapping(target = "id", ignore = true)
	void updateEntityFromDTO(ClienteDTO dto, @MappingTarget Cliente cliente);
	
	

}
