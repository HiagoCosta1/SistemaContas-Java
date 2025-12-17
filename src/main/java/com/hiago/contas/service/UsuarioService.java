package com.hiago.contas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiago.contas.domain.Usuario;
import com.hiago.contas.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	
	public List<Usuario> buscarTodos(){
		return usuarioRepository.findAll();
	}
	
	public Optional<Usuario> buscarPorId(Long id){
		return usuarioRepository.findById(id);
	}
	
	public Optional<Usuario> buscarPorEmail(String email){
		return usuarioRepository.findByEmail(email);
	}
	
	public Usuario salvar(Usuario usuario) {
		if(usuario.getId() == null && usuarioRepository.existsByEmail(usuario.getEmail())) {
			throw new RuntimeException("Email ja cadastrado");
		}
		return usuarioRepository.save(usuario);
	}
	
	public Usuario atualizar(Long id, Usuario usuarioAtualizado) {
		Optional<Usuario> usuarioExistente = usuarioRepository.findById(id);						//puxar o id no banco e pegar e transformar em Optional<Usuario> 
		if(usuarioExistente.isPresent()) {															//verificar se esse usuario criado tem de fato
			Usuario usuario = usuarioExistente.get();												//usar o metodo get do Optional para pegar as informacões e colocar no usuario
			usuario.setNome(usuarioAtualizado.getNome());											//usar o get no usuarioAtualizado e setar elas no usuario criado
			usuario.setEmail(usuarioAtualizado.getEmail());					
			if(usuarioAtualizado.getSenha() != null && !usuarioAtualizado.getSenha().isEmpty()) {	//só atualiza a senha se ela foi fornecida
				usuario.setSenha(usuarioAtualizado.getSenha());
			}
			return usuarioRepository.save(usuario);
		}
		throw new RuntimeException("Usuario nao encontrado!");
	}
	
	public void deletar(Long id) {
		if(!usuarioRepository.existsById(id)) {
			throw new RuntimeException("Usuario nao encontrado!");
		}
		usuarioRepository.deleteById(id);
	}
	
	
	//metodo para login
	public Optional<Usuario> login(String email, String senha){
		Optional<Usuario> usuario = usuarioRepository.findByEmail(email);			//pegar o email no parametro e puxar no banco, e colocando na variavel usuario
		if(usuario.isPresent() && usuario.get().getSenha().equals(senha)){			//validar se o usuario tem no banco e se a senha que esta salva no banco é igual(equals) com a senha fornecida
			return usuario;															//se tiver correto, vai retornar o usuario
		}
		return Optional.empty();													//se não retorna o Optional vazio
	}		

}
