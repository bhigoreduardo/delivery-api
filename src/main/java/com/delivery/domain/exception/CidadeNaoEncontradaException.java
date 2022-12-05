package com.delivery.domain.exception;

public class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = -3756457083602144228L;

	public CidadeNaoEncontradaException(String message) {
		super(message);
	}

	public CidadeNaoEncontradaException(Long cidadeId) {
		this(String.format("Cidade cód. %d não foi encontrada.", cidadeId));
	}

}
