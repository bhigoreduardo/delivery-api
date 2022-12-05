package com.delivery.domain.exception;

public class ProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 373907190958357621L;

	public ProdutoNaoEncontradoException(String message) {
		super(message);
	}

	public ProdutoNaoEncontradoException(Long produtoId) {
		this(String.format("Produto cód. %d não foi encontrado.", produtoId));
	}

}
