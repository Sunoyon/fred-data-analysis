package com.fred.sync.exceptions;

public class BaseException extends RuntimeException {
	private static final long serialVersionUID = 5746184876690596414L;
	public int statusCode;

	public BaseException() {
		super("");
	}

	public BaseException(String message) {
		super(message);
	}
}
