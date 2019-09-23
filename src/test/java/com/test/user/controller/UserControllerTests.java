package com.test.user.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import com.test.user.UserApplicationTests;
import com.test.user.entity.UserEntity;
import com.test.user.enums.ResponseStatus;
import com.test.user.exception.UserAlreadyExistsException;
import com.test.user.exception.UserNotFoundException;
import com.test.user.model.User;
import com.test.user.model.UserResponse;
import com.test.user.service.UserService;

public class UserControllerTests extends UserApplicationTests {

	@Test
	public void addUserTest() throws UserAlreadyExistsException {
		UserService userService = Mockito.mock(UserService.class);
		UserController userController = new UserController(userService);

		UserEntity userEntity = new UserEntity();
		userEntity.setFirstName("suresh");
		userEntity.setLastName("kumar");
		userEntity.setPhoneNumber("9182877790");
		userEntity.setUserId(1000L);

		when(userService.addUser(any(User.class))).thenReturn(userEntity);

		User user = new User();
		user.setFirstName("suresh");
		user.setLastName("kumar");
		user.setPhoneNumber("9182877790");

		ResponseEntity<UserResponse> addUserResEntity = userController.addUser(user);
		Assert.assertEquals(addUserResEntity.getBody().getHttpStatus(), 200);
		Assert.assertEquals(addUserResEntity.getBody().getStatus(), ResponseStatus.SUCCESS);
	}

	@Test(expected = UserAlreadyExistsException.class)
	public void userAlreadyExistsTest() throws UserAlreadyExistsException {
		UserService userService = Mockito.mock(UserService.class);
		UserController userController = new UserController(userService);
		when(userService.addUser(any(User.class))).thenThrow(new UserAlreadyExistsException("User already exists"));
		User user = new User();
		user.setFirstName("suresh");
		user.setLastName("kumar");
		user.setPhoneNumber("9182877790");
		userController.addUser(user);
	}

	@Test
	public void getUserTest() throws UserNotFoundException {
		UserService userService = Mockito.mock(UserService.class);
		UserController userController = new UserController(userService);
		UserEntity userEntity = new UserEntity();
		userEntity.setFirstName("suresh");
		userEntity.setLastName("kumar");
		userEntity.setPhoneNumber("9182877790");
		userEntity.setUserId(1000L);
		when(userService.getUser(Mockito.anyLong())).thenReturn(userEntity);
		ResponseEntity<UserResponse> userResponseEntity = userController.getUser(1000L);
		Assert.assertEquals(userResponseEntity.getBody().getHttpStatus(), 200);
		Assert.assertEquals(userResponseEntity.getBody().getStatus(), ResponseStatus.SUCCESS);
	}

	@Test(expected = UserNotFoundException.class)
	public void userNotFoundTest() throws UserNotFoundException {
		UserService userService = Mockito.mock(UserService.class);
		UserController userController = new UserController(userService);
		when(userService.getUser(Mockito.anyLong())).thenThrow(new UserNotFoundException("User not found"));
		userController.getUser(1000L);
	}

	@Test
	public void getAllUserTest() {
		UserService userService = Mockito.mock(UserService.class);
		UserController userController = new UserController(userService);
		UserEntity userEntity = new UserEntity();
		userEntity.setFirstName("suresh");
		userEntity.setLastName("kumar");
		userEntity.setPhoneNumber("9182877790");
		userEntity.setUserId(1000L);
		List<UserEntity> userEntityList = new ArrayList<UserEntity>();
		userEntityList.add(userEntity);

		when(userService.getAllUsers()).thenReturn(userEntityList);
		ResponseEntity<UserResponse> userResponseEntity = userController.getAllUsers();
		Assert.assertEquals(userResponseEntity.getBody().getHttpStatus(), 200);
		Assert.assertEquals(userResponseEntity.getBody().getStatus(), ResponseStatus.SUCCESS);
	}

	@Test
	public void updateUserTest() throws UserNotFoundException {
		UserService userService = Mockito.mock(UserService.class);
		UserController userController = new UserController(userService);
		UserEntity userEntity = new UserEntity();
		userEntity.setFirstName("suresh");
		userEntity.setLastName("kumar");
		userEntity.setPhoneNumber("9182877790");
		userEntity.setUserId(1000L);

		when(userService.updateUser(any(User.class))).thenReturn(userEntity);

		User user = new User();
		user.setFirstName("suresh");
		user.setLastName("kumar");
		user.setPhoneNumber("9182877790");

		ResponseEntity<UserResponse> addUserResEntity = userController.updateUser(user);
		Assert.assertEquals(addUserResEntity.getBody().getHttpStatus(), 200);
		Assert.assertEquals(addUserResEntity.getBody().getStatus(), ResponseStatus.SUCCESS);
	}

	@Test(expected = UserNotFoundException.class)
	public void updateUserNotFoundTest() throws UserNotFoundException {
		UserService userService = Mockito.mock(UserService.class);
		UserController userController = new UserController(userService);
		when(userService.updateUser(any(User.class))).thenThrow(new UserNotFoundException("User not found"));
		User user = Mockito.mock(User.class);
		userController.updateUser(user);
	}

	@Test(expected = UserNotFoundException.class)
	public void deleteUserNotFoundTest() throws UserNotFoundException {
		UserService userService = Mockito.mock(UserService.class);
		UserController userController = new UserController(userService);
		when(userService.deleteUser(Mockito.anyLong())).thenThrow(new UserNotFoundException("User not found"));
		userController.deleteUser(10000L);
	}

	@Test
	public void deleteUserTest() throws UserNotFoundException {
		UserService userService = Mockito.mock(UserService.class);
		UserController userController = new UserController(userService);
		when(userService.deleteUser(Mockito.anyLong())).thenReturn(true);
		ResponseEntity<UserResponse> deleteUser = userController.deleteUser(10000L);
		Assert.assertEquals(deleteUser.getBody().getHttpStatus(), 200);
		Assert.assertEquals(deleteUser.getBody().getStatus(), ResponseStatus.SUCCESS);
	}

	@Test
	public void faileToDeleteUserTest() throws UserNotFoundException {
		UserService userService = Mockito.mock(UserService.class);
		UserController userController = new UserController(userService);
		when(userService.deleteUser(Mockito.anyLong())).thenReturn(false);
		ResponseEntity<UserResponse> deleteUser = userController.deleteUser(10000L);
		Assert.assertEquals(deleteUser.getBody().getHttpStatus(), 200);
		Assert.assertEquals(deleteUser.getBody().getStatus(), ResponseStatus.FAILURE);
	}
}
