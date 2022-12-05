package com.delivery.domain.exception;

public class EstadoJaCadastradoException extends EntidadeJaCadastradaException {

	private static final long serialVersionUID = -5529358706644573314L;

	public EstadoJaCadastradoException(String estadoNome) {
		super(String.format("Estado nome %s já foi cadastrado.", estadoNome));
	}

}
