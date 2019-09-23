package com.test.user.exception;

public class UserNotFoundException extends BaseException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2633961290490038825L;
	public UserNotFoundException(String message){
		super(message);
	}
}
