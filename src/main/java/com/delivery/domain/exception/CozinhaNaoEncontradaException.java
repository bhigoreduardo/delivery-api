package com.delivery.domain.exception;

public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 8808355457301166526L;

	public CozinhaNaoEncontradaException(String message) {
		super(message);
	}

	public CozinhaNaoEncontradaException(Long cozinhaId) {
		this(String.format("Cozinha cód. %d não foi encontrada.", cozinhaId));
	}

}
