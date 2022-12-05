package com.delivery.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.delivery.domain.exception.EntidadeEmUsoException;
import com.delivery.domain.exception.FormaPagamentoNaoEncontradaException;
import com.delivery.domain.model.FormaPagamento;
import com.delivery.domain.repository.FormaPagamentoRepository;

@Service
public class FormaPagamentoService {
	
	private static final String FORMA_PAGAMENTO_EM_USO = "Forma de Pagamento cód. %d está em uso.";

	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;
	
	public FormaPagamento save(FormaPagamento formaPagamento) {
		return formaPagamentoRepository.save(formaPagamento);
	}

	@Transactional
	public void deleteById(Long formaPagamentoId) {
		try {
			formaPagamentoRepository.deleteById(formaPagamentoId);
			formaPagamentoRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new FormaPagamentoNaoEncontradaException(formaPagamentoId);
			
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(FORMA_PAGAMENTO_EM_USO, formaPagamentoId));
		}
	}

	public FormaPagamento findById(Long formaPagamentoId) {
		return formaPagamentoRepository.findById(formaPagamentoId)
				.orElseThrow(() -> new FormaPagamentoNaoEncontradaException(formaPagamentoId));
	}

}
