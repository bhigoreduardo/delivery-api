package com.delivery.domain.infraestructure.service.storage;

public class StorageException extends RuntimeException {

	private static final long serialVersionUID = 7411462653586908900L;

	public StorageException(String message) {
		super(message);
	}

	public StorageException(String message, Throwable cause) {
		super(message, cause);
	}

}
