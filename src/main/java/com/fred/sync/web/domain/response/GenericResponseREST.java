package com.fred.sync.web.domain.response;


import java.io.Serializable;

public class GenericResponseREST implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -525921262768938017L;
	public String message;

	public GenericResponseREST() {
	}

	public GenericResponseREST(String message) {
		this.message = message;
	}
}
