package com.test.user.exception;

public class BaseException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3638002626739259060L;
	private String message;

	BaseException(String message) {
		super(message);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
