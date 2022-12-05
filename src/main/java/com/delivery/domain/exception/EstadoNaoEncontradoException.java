package com.delivery.domain.exception;

public class EstadoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = -2830574356953318889L;

	public EstadoNaoEncontradoException(String message) {
		super(message);
	}

	public EstadoNaoEncontradoException(Long estadoId) {
		this(String.format("Estado cód. %d não foi encontrado.", estadoId));
	}

}
