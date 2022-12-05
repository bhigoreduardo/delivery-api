package com.delivery.domain.exception;

public class CidadeJaCadastradaException extends EntidadeJaCadastradaException {

	private static final long serialVersionUID = -213978570056375329L;

	public CidadeJaCadastradaException(String cidadeNome, String estadoNome) {
		super(String.format("Cidade %s já foi cadastrada no Estado %s.", cidadeNome, estadoNome));
	}

}
