package com.test.user.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.user.entity.UserEntity;
import com.test.user.enums.ResponseStatus;
import com.test.user.exception.UserAlreadyExistsException;
import com.test.user.exception.UserNotFoundException;
import com.test.user.model.KeyValueMessage;
import com.test.user.model.User;
import com.test.user.model.UserResponse;
import com.test.user.service.UserService;

@RestController
public class UserController {
	private final Logger logger = LoggerFactory.getLogger(UserController.class);

	private UserService userservice;

	@Autowired
	UserController(UserService userservice) {
		this.userservice = userservice;
	}

	@PostMapping("/addUser")
	public ResponseEntity<UserResponse> addUser(@RequestBody @Valid User user) throws UserAlreadyExistsException {
		try {
			UserEntity addUser = userservice.addUser(user);
			UserResponse userResponse = getUserResponse(HttpStatus.OK.value(), ResponseStatus.SUCCESS, addUser);
			return new ResponseEntity<UserResponse>(userResponse, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.toString(), e);
			throw e;
		}
	}

	@GetMapping("/getUser/{userId}")
	public ResponseEntity<UserResponse> getUser(@RequestParam("userId") @NotNull Long userId)
			throws UserNotFoundException {
		try {

			UserEntity user = userservice.getUser(userId);
			UserResponse userResponse = getUserResponse(HttpStatus.OK.value(), ResponseStatus.SUCCESS, user);
			return new ResponseEntity<UserResponse>(userResponse, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.toString(), e);
			throw e;
		}
	}

	@GetMapping("/getAllUsers")
	public ResponseEntity<UserResponse> getAllUsers() {
		try {
			List<UserEntity> allUsers = userservice.getAllUsers();
			UserResponse userResponse = getUserResponse(HttpStatus.OK.value(), ResponseStatus.SUCCESS, allUsers);
			return new ResponseEntity<UserResponse>(userResponse, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.toString(), e);
			throw e;
		}

	}

	@PutMapping("/updateUser")
	public ResponseEntity<UserResponse> updateUser(@RequestBody @Valid User user) throws UserNotFoundException {
		try {
			UserEntity updateUser = userservice.updateUser(user);
			UserResponse userResponse = getUserResponse(HttpStatus.OK.value(), ResponseStatus.SUCCESS, updateUser);
			return new ResponseEntity<UserResponse>(userResponse, HttpStatus.OK);

		} catch (Exception e) {
			logger.error(e.toString(), e);
			throw e;
		}
	}

	@DeleteMapping("/deleteUser/{userId}")
	public ResponseEntity<UserResponse> deleteUser(@RequestParam("userId") @NotNull Long userId)
			throws UserNotFoundException {
		try {
			boolean isDeleted = userservice.deleteUser(userId);
			logger.debug("isDeleted:" + isDeleted);
			KeyValueMessage message = new KeyValueMessage();
			message.setKey("message");
			UserResponse userResponse = null;
			if (isDeleted) {
				message.setMessage("User has been deleted succesfully");
				userResponse = getUserResponse(HttpStatus.OK.value(), ResponseStatus.SUCCESS, message);
			} else {
				message.setMessage("User has not been deleted succesfully");
				userResponse = getUserResponse(HttpStatus.OK.value(), ResponseStatus.FAILURE, message);
			}
			return new ResponseEntity<UserResponse>(userResponse, HttpStatus.OK);

		} catch (Exception e) {
			logger.error(e.toString(), e);
			throw e;
		}
	}

	private UserResponse getUserResponse(int httpCode, ResponseStatus status, Object payload) {
		UserResponse userResponse = new UserResponse();
		userResponse.setHttpStatus(httpCode);
		userResponse.setPayload(payload);
		userResponse.setStatus(status);
		return userResponse;
	}
}
