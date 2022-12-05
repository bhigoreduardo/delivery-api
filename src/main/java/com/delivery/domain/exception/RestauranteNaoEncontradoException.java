package com.delivery.domain.exception;

public class RestauranteNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 7257126870509315809L;

	public RestauranteNaoEncontradoException(String message) {
		super(message);
	}

	public RestauranteNaoEncontradoException(Long restauranteId) {
		this(String.format("Restaurante cód. %d não foi encontrado.", restauranteId));
	}

}
