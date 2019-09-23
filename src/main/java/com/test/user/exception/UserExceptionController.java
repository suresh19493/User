package com.test.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.test.user.enums.ResponseStatus;
import com.test.user.model.KeyValueMessage;
import com.test.user.model.UserResponse;

@ControllerAdvice
public class UserExceptionController {

	@ExceptionHandler(value = BaseException.class)
	public ResponseEntity<UserResponse> exception(BaseException baseException) {
		UserResponse userResponse = new UserResponse();
		userResponse.setStatus(ResponseStatus.FAILURE);
		KeyValueMessage keyValueMessage = new KeyValueMessage();
		keyValueMessage.setKey("reason");
		keyValueMessage.setMessage(baseException.getMessage());
		userResponse.setPayload(keyValueMessage);
		HttpStatus status = null;
		if (baseException instanceof UserNotFoundException) {
			userResponse.setHttpStatus(HttpStatus.NOT_FOUND.value());
			status = HttpStatus.NOT_FOUND;

		} else if (baseException instanceof UserAlreadyExistsException) {
			userResponse.setHttpStatus(HttpStatus.CONFLICT.value());
			status = HttpStatus.CONFLICT;

		} else {
			userResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<>(userResponse, status);
	}

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<UserResponse> exception(Exception exception) {
		UserResponse userResponse = new UserResponse();
		userResponse.setStatus(ResponseStatus.FAILURE);
		KeyValueMessage keyValueMessage = new KeyValueMessage();
		keyValueMessage.setKey("reason");
		keyValueMessage.setMessage(exception.getMessage());
		userResponse.setPayload(keyValueMessage);
		userResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		return new ResponseEntity<>(userResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
