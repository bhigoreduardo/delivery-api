package com.delivery.domain.exception;

public class PermissaoNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = -8433998847496289566L;

	public PermissaoNaoEncontradaException(String message) {
		super(message);
	}

	public PermissaoNaoEncontradaException(Long permissaoId) {
		this(String.format("Permissão cód. %d não foi encontrada.", permissaoId));
	}

}
