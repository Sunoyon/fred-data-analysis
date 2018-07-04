package com.fred.sync.exceptions;

import org.apache.http.HttpStatus;


public class InternalIOException extends GenericAppException {

	private static final long serialVersionUID = -7337190775260909097L;

	public InternalIOException() {
		super("Internal IO Exception");
		this.statusCode = HttpStatus.SC_INTERNAL_SERVER_ERROR;
	}

	public InternalIOException(String message) {
		super(message);
		this.statusCode = HttpStatus.SC_INTERNAL_SERVER_ERROR;
	}
}