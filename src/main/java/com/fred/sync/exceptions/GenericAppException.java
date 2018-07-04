package com.fred.sync.exceptions;

public class GenericAppException extends RuntimeException {

	private static final long serialVersionUID = 7479927555244175350L;

	private static final Integer DEFAULT_STATUS_CODE = -1;
	public Integer statusCode;
	public String exceptionReason;

	public GenericAppException() {
	}

	public GenericAppException(String message) {
		this(message, message);
	}

	public GenericAppException(String message, String exceptionReason) {
		this(message, exceptionReason, DEFAULT_STATUS_CODE);
	}

	public GenericAppException(String message, Integer statusCode) {
		this(message, message, statusCode);
	}

	public GenericAppException(String message, String exceptionReason, Integer statusCode) {
		super(message);
		this.exceptionReason = exceptionReason;
		this.statusCode = statusCode;
	}

}
