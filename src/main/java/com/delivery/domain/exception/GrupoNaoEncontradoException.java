package com.delivery.domain.exception;

public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 7715596879390393125L;

	public GrupoNaoEncontradoException(String message) {
		super(message);
	}

	public GrupoNaoEncontradoException(Long grupoId) {
		this(String.format("Grupo cód. %d não foi encontrado.", grupoId));
	}

}
