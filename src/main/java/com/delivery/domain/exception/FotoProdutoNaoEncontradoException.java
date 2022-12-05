package com.delivery.domain.exception;

public class FotoProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 6913052790188800491L;

	public FotoProdutoNaoEncontradoException(String message) {
		super(message);
	}

	public FotoProdutoNaoEncontradoException(Long restauranteId, Long produtoId) {
		this(String.format("Não existe um cadastro de foto de produto com cód. %d para o restaurante de cód. %d",
				produtoId, restauranteId));
	}
}
