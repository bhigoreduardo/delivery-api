package com.delivery.domain.exception;

public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = -4989323194842842552L;

	public UsuarioNaoEncontradoException(String message) {
		super(message);
	}

	public UsuarioNaoEncontradoException(Long usuarioId) {
		this(String.format("Usuário cód. %d não foi encontrado.", usuarioId));
	}

}
