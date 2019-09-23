package com.test.user.exception;

public class UserAlreadyExistsException extends BaseException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6780380652714374620L;

	public UserAlreadyExistsException(String message) {
		super(message);
	}
}
