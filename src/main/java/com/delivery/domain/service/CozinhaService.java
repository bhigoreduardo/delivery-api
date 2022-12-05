package com.delivery.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.delivery.domain.exception.CozinhaNaoEncontradaException;
import com.delivery.domain.exception.EntidadeEmUsoException;
import com.delivery.domain.model.Cozinha;
import com.delivery.domain.repository.CozinhaRepository;

@Service
public class CozinhaService {
	
	private static final String COZINHA_EM_USO = "Cozinha cód. %d está em uso.";

	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	public Cozinha save(Cozinha cozinha) {
		return cozinhaRepository.save(cozinha);
	}

	@Transactional
	public void deleteById(Long cozinhaId) {
		try {
			cozinhaRepository.deleteById(cozinhaId);
			cozinhaRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new CozinhaNaoEncontradaException(cozinhaId);
			
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(COZINHA_EM_USO, cozinhaId));
		}
	}

	public Cozinha findById(Long cozinhaId) {
		return cozinhaRepository.findById(cozinhaId)
				.orElseThrow(() -> new CozinhaNaoEncontradaException(cozinhaId));
	}

}
