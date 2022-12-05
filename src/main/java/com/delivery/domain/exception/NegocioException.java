package com.delivery.domain.exception;

public class NegocioException extends RuntimeException {

	private static final long serialVersionUID = 8099240436655285778L;
	
	public NegocioException(String message) {
		super(message);
	}
	
	public NegocioException(String message, Throwable cause) {
		super(message, cause);
	}

}
