package com.delivery.domain.exception;

public class FormaPagamentoNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = -5347849347508358090L;

	public FormaPagamentoNaoEncontradaException(String message) {
		super(message);
	}

	public FormaPagamentoNaoEncontradaException(Long formaPagamentoId) {
		this(String.format("Forma de PagamentoId cód. %d não foi encontrada.", formaPagamentoId));
	}

}
