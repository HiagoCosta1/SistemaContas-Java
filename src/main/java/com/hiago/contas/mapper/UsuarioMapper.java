package com.hiago.contas.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.hiago.contas.domain.Usuario;
import com.hiago.contas.dto.UsuarioDTO;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {


	@Mapping(target = "senha", ignore = true)
	UsuarioDTO toDTO(Usuario usuario);
	
	Usuario toEntity(UsuarioDTO dto);
	
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "senha", ignore = true)
	void updateEntityFromDTO(UsuarioDTO dto, @MappingTarget Usuario usuario);

}
