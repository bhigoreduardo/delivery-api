package com.delivery.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 7349072873076256966L;

	public PedidoNaoEncontradoException(String codigoPedido) {
		super(String.format("Pedido cód. %s não foi encontrado.", codigoPedido));
	}

}
