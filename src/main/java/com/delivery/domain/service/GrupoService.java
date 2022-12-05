package com.delivery.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.delivery.domain.exception.EntidadeEmUsoException;
import com.delivery.domain.exception.GrupoNaoEncontradoException;
import com.delivery.domain.model.Grupo;
import com.delivery.domain.model.Permissao;
import com.delivery.domain.repository.GrupoRepository;

@Service
public class GrupoService {
	
	private static final String GRUPO_EM_USO = "Grupo cód. %d está em uso.";
	
	@Autowired
	private PermissaoService permissaoService;

	@Autowired
	private GrupoRepository grupoRepository;
	
	public Grupo save(Grupo grupo) {
		return grupoRepository.save(grupo);
	}

	@Transactional
	public void deleteById(Long grupoId) {
		try {
			grupoRepository.deleteById(grupoId);
			grupoRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new GrupoNaoEncontradoException(grupoId);
			
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(GRUPO_EM_USO, grupoId));
		}
	}

	public Grupo findById(Long grupoId) {
		return grupoRepository.findById(grupoId)
				.orElseThrow(() -> new GrupoNaoEncontradoException(grupoId));
	}
	
	@Transactional
	public void removePermissao(Long grupoId, Long permissaoId) {
	    Grupo grupo = findById(grupoId);
	    Permissao permissao = permissaoService.findById(permissaoId);
	    
	    grupo.removePermissao(permissao);
	}
	

	@Transactional
	public void addPermissao(Long grupoId, Long permissaoId) {
		Grupo grupo = findById(grupoId);
	    Permissao permissao = permissaoService.findById(permissaoId);
	    
	    grupo.addPermissao(permissao);
	} 

}
