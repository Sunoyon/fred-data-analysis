package com.fred.sync.exceptions;


public class Non200Exception extends GenericAppException {
	private static final long serialVersionUID = -6191947140822236396L;

	public Non200Exception(String message) {
		super(message);
	}

	public Non200Exception(String message, String response) {
		super(message, response);
	}
}
