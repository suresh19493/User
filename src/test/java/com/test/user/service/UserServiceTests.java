package com.test.user.service;

import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.test.user.UserApplicationTests;
import com.test.user.entity.UserEntity;
import com.test.user.exception.UserAlreadyExistsException;
import com.test.user.exception.UserNotFoundException;
import com.test.user.mapper.UserMapper;
import com.test.user.mapper.UserMapperImpl;
import com.test.user.model.User;
import com.test.user.repository.UserRepository;

public class UserServiceTests extends UserApplicationTests {
	private UserRepository userRepository = null;
	private UserMapper userMapper = null;
	private UserService userService = null;

	@Before
	public void init() {
		userRepository = Mockito.mock(UserRepository.class);
		userMapper = new UserMapperImpl();
		userService = new UserServiceImpl(userRepository, userMapper);
	}

	@Test
	public void addUserTest() throws UserAlreadyExistsException {
		User user = new User();
		user.setFirstName("suresh");
		user.setLastName("kumar");
		user.setPhoneNumber("9182877790");

		UserEntity userEntity = new UserEntity();
		userEntity.setFirstName("suresh");
		userEntity.setLastName("kumar");
		userEntity.setPhoneNumber("9182877790");
		userEntity.setUserId(1000L);
		Mockito.when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

		UserEntity addUser = userService.addUser(user);
		Assert.assertEquals(addUser.getFirstName(), "suresh");
		Assert.assertEquals(addUser.getLastName(), "kumar");
		Assert.assertEquals(addUser.getPhoneNumber(), "9182877790");
		Assert.assertEquals(addUser.getUserId(), new Long(1000));

	}

	@Test(expected = UserAlreadyExistsException.class)
	public void addUserAlreadyExistsTest() throws UserAlreadyExistsException {

		User user = new User();
		user.setFirstName("suresh");
		user.setLastName("kumar");
		user.setPhoneNumber("9182877790");
		UserEntity userEntity = Mockito.mock(UserEntity.class);
		Mockito.when(userRepository.getUserByPhoneNumber(Mockito.anyString())).thenReturn(userEntity);
		userService.addUser(user);
	}

	@Test
	public void getUserTest() throws UserNotFoundException {

		UserEntity userEntity = new UserEntity();
		userEntity.setFirstName("suresh");
		userEntity.setLastName("kumar");
		userEntity.setPhoneNumber("9182877790");
		userEntity.setUserId(1000L);
		Mockito.when(userRepository.getOne(Mockito.anyLong())).thenReturn(userEntity);
		UserEntity user = userService.getUser(1000L);
		Assert.assertEquals(user.getFirstName(), "suresh");
		Assert.assertEquals(user.getLastName(), "kumar");
		Assert.assertEquals(user.getPhoneNumber(), "9182877790");
		Assert.assertEquals(user.getUserId(), new Long(1000));
	}

	@Test(expected = UserNotFoundException.class)
	public void getUserNotFoundTest() throws UserNotFoundException {

		Mockito.when(userRepository.getOne(Mockito.anyLong())).thenReturn(null);
		userService.getUser(1000L);
	}

	@Test
	public void getAllUserTest() {
		UserEntity userEntity = new UserEntity();
		userEntity.setFirstName("suresh");
		userEntity.setLastName("kumar");
		userEntity.setPhoneNumber("9182877790");
		userEntity.setUserId(1000L);
		List<UserEntity> userEntityList = new ArrayList<UserEntity>();
		userEntityList.add(userEntity);
		Mockito.when(userRepository.findAll()).thenReturn(userEntityList);
		List<UserEntity> allUsers = userService.getAllUsers();
		Assert.assertEquals(allUsers.size(), 1);
	}

	@Test
	public void updateUserTest() throws UserNotFoundException {
		UserEntity userEntity = new UserEntity();
		userEntity.setFirstName("suresh");
		userEntity.setLastName("Emmadisetti");
		userEntity.setPhoneNumber("9182877790");
		userEntity.setUserId(1000L);
		User user = new User();
		user.setFirstName("suresh");
		user.setLastName("kumar");
		user.setPhoneNumber("9182877790");
		user.setUserId(1000L);

		Mockito.when(userRepository.getOne(Mockito.anyLong())).thenReturn(userEntity);
		Mockito.when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

		UserEntity updateUser = userService.updateUser(user);
		Assert.assertEquals(updateUser.getFirstName(), "suresh");
		Assert.assertEquals(updateUser.getLastName(), "Emmadisetti");
		Assert.assertEquals(updateUser.getPhoneNumber(), "9182877790");
		Assert.assertEquals(updateUser.getUserId(), new Long(1000));

	}

	@Test(expected = UserNotFoundException.class)
	public void updateUserNotFoundTest() throws UserNotFoundException {
		Mockito.when(userRepository.getOne(Mockito.anyLong())).thenReturn(null);
		User user = new User();
		user.setFirstName("suresh");
		user.setLastName("kumar");
		user.setPhoneNumber("9182877790");
		user.setUserId(1000L);
		userService.updateUser(user);
	}

	@Test(expected = UserNotFoundException.class)
	public void deleteUserNotFoundTest() throws UserNotFoundException {
		Mockito.when(userRepository.getOne(Mockito.anyLong())).thenReturn(null);
		userService.deleteUser(1000L);
	}

	@Test
	public void deleteUserTest() throws UserNotFoundException {
		UserEntity userEntity = Mockito.mock(UserEntity.class);
		Mockito.when(userRepository.getOne(Mockito.anyLong())).thenReturn(userEntity);
		boolean deleteUser = userService.deleteUser(1000L);
		Assert.assertEquals(deleteUser, true);
	}

	@Test
	public void getUserByPhoneNumberTest() {
		UserEntity userEntity = Mockito.mock(UserEntity.class);
		Mockito.when(userRepository.getUserByPhoneNumber(Mockito.anyString())).thenReturn(userEntity);
		UserEntity userByPhoneNumber = userService.getUserByPhoneNumber("9182877799");
		Assert.assertEquals(userByPhoneNumber.getFirstName(), userEntity.getFirstName());
	}

}
