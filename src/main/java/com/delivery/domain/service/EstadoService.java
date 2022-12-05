package com.delivery.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.delivery.domain.exception.EntidadeEmUsoException;
import com.delivery.domain.exception.EstadoJaCadastradoException;
import com.delivery.domain.exception.EstadoNaoEncontradoException;
import com.delivery.domain.model.Estado;
import com.delivery.domain.repository.EstadoRepository;

@Service
public class EstadoService {

	private static final String ESTADO_EM_USO = "Estado cód. %d está em uso.";

	@Autowired
	private EstadoRepository estadoRepository;

	@Transactional
	public Estado save(Estado estado) {
		try {
			return estadoRepository.save(estado);
		} catch (DataIntegrityViolationException ex) {
			throw new EstadoJaCadastradoException(estado.getNome());
		}
	}

	@Transactional
	public void deleteById(Long estadoId) {
		try {
			estadoRepository.deleteById(estadoId);
			estadoRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new EstadoNaoEncontradoException(estadoId);

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(ESTADO_EM_USO, estadoId));
		}
	}

	public Estado findById(Long estadoId) {
		return estadoRepository.findById(estadoId)
				.orElseThrow(() -> new EstadoNaoEncontradoException(estadoId));
	}

}
