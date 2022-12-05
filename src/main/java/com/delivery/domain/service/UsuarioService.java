package com.delivery.domain.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.delivery.domain.exception.NegocioException;
import com.delivery.domain.exception.UsuarioNaoEncontradoException;
import com.delivery.domain.model.Grupo;
import com.delivery.domain.model.Usuario;
import com.delivery.domain.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private GrupoService grupoService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Transactional
	public Usuario save(Usuario usuario) {
		usuarioRepository.detach(usuario); // Não irá sincronizar as alterações nesse objeto ao final da transação

		Optional<Usuario> usuarioExists = usuarioRepository.findByEmail(usuario.getEmail());

		if (usuarioExists.isPresent() && !usuarioExists.get().equals(usuario)) {
			throw new NegocioException(
					String.format("Já existe usuário cadastrado com o e-mail: %s", usuario.getEmail()));
		}

		if (usuario.isNewUsuario()) {
			usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
		}
		
		return usuarioRepository.save(usuario);
	}
	
	@Transactional
	public void setSenha(Long usuarioId, String senhaCurrent, String newSenha) {
		Usuario usuario = findById(usuarioId);

		if (!passwordEncoder.matches(senhaCurrent, usuario.getSenha())) {
			throw new NegocioException("Senha atual informada não coincide com a senha do usuário.");
		}
		
		usuario.setSenha(passwordEncoder.encode(newSenha));
	}
	
	public Usuario findById(Long usuarioId) {
		return usuarioRepository.findById(usuarioId)
			.orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));
	}
	
	@Transactional
	public void removeGrupo(Long usuarioId, Long grupoId) {
		Usuario usuario = findById(usuarioId);
		Grupo grupo = grupoService.findById(grupoId);
		
		usuario.removeGrupo(grupo);
	}

	@Transactional
	public void addGrupo(Long usuarioId, Long grupoId) {
		Usuario usuario = findById(usuarioId);
		Grupo grupo = grupoService.findById(grupoId);
		
		usuario.addGrupo(grupo);
	}

}
