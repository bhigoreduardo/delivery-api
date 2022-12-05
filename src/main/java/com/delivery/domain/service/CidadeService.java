package com.delivery.domain.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.delivery.domain.exception.CidadeJaCadastradaException;
import com.delivery.domain.exception.CidadeNaoEncontradaException;
import com.delivery.domain.exception.EntidadeEmUsoException;
import com.delivery.domain.model.Cidade;
import com.delivery.domain.model.Estado;
import com.delivery.domain.repository.CidadeRepository;

@Service
public class CidadeService {
	
	private static final String CIDADE_EM_USO = "Cidade cód. %d está em uso.";
	
	@Autowired
	private EstadoService estadoService;

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Transactional
	public Cidade save(Cidade cidade) {
		Long estadoId = cidade.getEstado().getId();

		Estado estado = estadoService.findById(estadoId);
		cidade.setEstado(estado);
		
		Optional<Cidade> cidadeExists = cidadeRepository.findByNomeAndEstado(estado.getNome(), estado);
		if (cidadeExists.isPresent()) {
			System.out.println("Tem");
			throw new CidadeJaCadastradaException(cidade.getNome(), estado.getNome());
		}

		return cidadeRepository.save(cidade);
	}

	@Transactional
	public void deleteById(Long cidadeId) {
		try {
			cidadeRepository.deleteById(cidadeId);
			cidadeRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new CidadeNaoEncontradaException(cidadeId);
			
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(CIDADE_EM_USO, cidadeId));
		}
	}

	public Cidade findById(Long cidadeId) {
		return cidadeRepository.findById(cidadeId)
				.orElseThrow(() -> new CidadeNaoEncontradaException(cidadeId));
	}

}
