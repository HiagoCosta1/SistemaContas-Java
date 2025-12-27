package com.hiago.contas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiago.contas.domain.Usuario;
import com.hiago.contas.exception.BusinessException;
import com.hiago.contas.exception.ResourceNotFoundException;
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
	
	public Usuario buscarPorIdOuLancarErro(Long id) {
		return usuarioRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Usuario com ID " + id +  "não encontrado"));
	}
	
	public Optional<Usuario> buscarPorEmail(String email){
		return usuarioRepository.findByEmail(email);
	}
	
	public Usuario salvar(Usuario usuario) {
		if(usuario.getId() == null && usuarioRepository.existsByEmail(usuario.getEmail())) {
			throw new BusinessException("Já existe um usuario cadastrado com o email: " + usuario.getEmail());
		}
		return usuarioRepository.save(usuario);
	}
	
	public Usuario atualizar(Long id, Usuario usuario) {
		Usuario usuarioExistente = buscarPorIdOuLancarErro(id);
		
		if(!usuarioExistente.getEmail().equals(usuario.getEmail())&& usuarioRepository.existsByEmail(usuario.getEmail())) {
			throw new BusinessException("Já existe outro usuario com o email: " + usuario.getEmail());
		}
		
		usuarioExistente.setNome(usuario.getNome());
		usuarioExistente.setEmail(usuario.getEmail());
		
		return usuarioRepository.save(usuarioExistente);
		
	}
	
	public void deletar(Long id) {
		Usuario usuario = buscarPorIdOuLancarErro(id);
		usuarioRepository.delete(usuario);
	}
	
	
	//metodo para login
	public Optional<Usuario> login(String email, String senha){
		Optional<Usuario> usuario = usuarioRepository.findByEmail(email);			//pegar o email no parametro e puxar no banco, e colocando na variavel usuario
		if(usuario.isPresent() && usuario.get().getSenha().equals(senha)){			//validar se o usuario tem no banco e se a senha que esta salva no banco é igual(equals) com a senha fornecida
			return usuario;															//se tiver correto, vai retornar o usuario
		}
		return Optional.empty();													//se não retorna o Optional vazio
	}		
	
	
	public void alterarSenha(Long id, String senhaAtual, String novaSenha) {
		Usuario usuario = buscarPorIdOuLancarErro(id);
		
		// Validação: senha atual está correta?
		if (!usuario.getSenha().equals(senhaAtual)) {
			throw new BusinessException("Senha atual incorreta");
		}
		
		// Validação: nova senha não pode ser igual à atual
		if (senhaAtual.equals(novaSenha)) {
			throw new BusinessException("A nova senha deve ser diferente da senha atual");
		}
		
		// Validação: tamanho mínimo
		if (novaSenha.length() < 6) {
			throw new BusinessException("A nova senha deve ter no mínimo 6 caracteres");
		}
		
		
		usuario.setSenha(novaSenha);
		usuarioRepository.save(usuario);
	}
	

}
