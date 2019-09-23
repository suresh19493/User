package com.test.user.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.user.UserApplication;
import com.test.user.UserApplicationTests;
import com.test.user.entity.UserEntity;
import com.test.user.enums.ResponseStatus;
import com.test.user.model.KeyValueMessage;
import com.test.user.model.User;
import com.test.user.model.UserResponse;
import com.test.user.repository.UserRepository;

@SpringBootTest(classes = UserApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserControllerIntegratedTests extends UserApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;
	@MockBean
	private UserRepository userRepository;
	private User user;
	private UserEntity userEntity;

	private ObjectMapper objectMapper = new ObjectMapper();

	@Before
	public void init() {
		user = new User();
		user.setFirstName("suresh");
		user.setLastName("emmadisetti");
		user.setPhoneNumber("9182877790");

		userEntity = new UserEntity();
		userEntity.setFirstName("suresh");
		userEntity.setLastName("kumar");
		userEntity.setPhoneNumber("9182877790");
		userEntity.setUserId(1000L);
	}

	@Test
	public void addUserTest() {
		Mockito.when(userRepository.getUserByPhoneNumber(Mockito.anyString())).thenReturn(null);
		Mockito.when(userRepository.save(Mockito.any(UserEntity.class))).thenReturn(userEntity);
		ResponseEntity<UserResponse> responseEntity = this.restTemplate
				.postForEntity("http://localhost:" + port + "/addUser", user, UserResponse.class);
		System.out.println("responseEntity:"+responseEntity);
		assertEquals(200, responseEntity.getStatusCodeValue());
		assertEquals(ResponseStatus.SUCCESS, responseEntity.getBody().getStatus());
		UserEntity user = objectMapper.convertValue(responseEntity.getBody().getPayload(), UserEntity.class);
		assertEquals(new Long(1000), user.getUserId());
		assertEquals("suresh", user.getFirstName());
		assertEquals("kumar", user.getLastName());
		assertEquals("9182877790", user.getPhoneNumber());

	}

	@Test
	public void userAlreadyExistsTest() {
		Mockito.when(userRepository.getUserByPhoneNumber(Mockito.anyString())).thenReturn(userEntity);
		ResponseEntity<UserResponse> responseEntity = this.restTemplate
				.postForEntity("http://localhost:" + port + "/addUser", user, UserResponse.class);
		assertEquals(409, responseEntity.getStatusCodeValue());
		assertEquals(ResponseStatus.FAILURE, responseEntity.getBody().getStatus());
		KeyValueMessage message = objectMapper.convertValue(responseEntity.getBody().getPayload(),
				KeyValueMessage.class);
		assertEquals(message.getKey(), "reason");
		assertEquals(message.getMessage(), "User Already Exists");
	}

	@Test
	public void getUserTest() {
		Mockito.when(userRepository.getOne(Mockito.anyLong())).thenReturn(userEntity);

		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<?> entity = new HttpEntity<>(headers);

		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl("http://localhost:" + port + "/getUser/{userId}").queryParam("userId", 1000L);
		ResponseEntity<UserResponse> responseEntity = this.restTemplate.exchange(builder.toUriString(), HttpMethod.GET,
				entity, UserResponse.class, 10000L);
		assertEquals(200, responseEntity.getStatusCodeValue());
		assertEquals(ResponseStatus.SUCCESS, responseEntity.getBody().getStatus());
		UserEntity user = objectMapper.convertValue(responseEntity.getBody().getPayload(), UserEntity.class);
		assertEquals(new Long(1000), user.getUserId());
		assertEquals("suresh", user.getFirstName());
		assertEquals("kumar", user.getLastName());
		assertEquals("9182877790", user.getPhoneNumber());

	}

	@Test
	public void getUserNotFoundTest() {
		Mockito.when(userRepository.getOne(Mockito.anyLong())).thenReturn(null);
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<?> entity = new HttpEntity<>(headers);

		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl("http://localhost:" + port + "/getUser/{userId}").queryParam("userId", 1000L);
		ResponseEntity<UserResponse> responseEntity = this.restTemplate.exchange(builder.toUriString(), HttpMethod.GET,
				entity, UserResponse.class);

		assertEquals(404, responseEntity.getStatusCodeValue());
		assertEquals(ResponseStatus.FAILURE, responseEntity.getBody().getStatus());
		KeyValueMessage message = objectMapper.convertValue(responseEntity.getBody().getPayload(),
				KeyValueMessage.class);
		assertEquals(message.getKey(), "reason");
		assertEquals(message.getMessage(), "User Not Found");

	}

	@Test
	public void getAllUserTest() {
		List<UserEntity> userEntityList = new ArrayList<>();
		userEntityList.add(userEntity);
		Mockito.when(userRepository.findAll()).thenReturn(userEntityList);
		ResponseEntity<UserResponse> responseEntity = this.restTemplate
				.getForEntity("http://localhost:" + port + "/getAllUsers", UserResponse.class);
		assertEquals(200, responseEntity.getStatusCodeValue());
		assertEquals(ResponseStatus.SUCCESS, responseEntity.getBody().getStatus());
		List userList = objectMapper.convertValue(responseEntity.getBody().getPayload(), List.class);
		assertEquals(1, userList.size());
	}

	@Test
	public void updateUserTest() {
		user.setUserId(1000L);
		Mockito.when(userRepository.getOne(Mockito.anyLong())).thenReturn(userEntity);
		userEntity.setLastName("setti");
		Mockito.when(userRepository.save(Mockito.any(UserEntity.class))).thenReturn(userEntity);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<User> entity = new HttpEntity<>(user, headers);

		ResponseEntity<UserResponse> responseEntity = this.restTemplate
				.exchange("http://localhost:" + port + "/updateUser", HttpMethod.PUT, entity, UserResponse.class);

		assertEquals(200, responseEntity.getStatusCodeValue());
		assertEquals(ResponseStatus.SUCCESS, responseEntity.getBody().getStatus());
		UserEntity user = objectMapper.convertValue(responseEntity.getBody().getPayload(), UserEntity.class);
		assertEquals(new Long(1000), user.getUserId());
		assertEquals("suresh", user.getFirstName());
		assertEquals("setti", user.getLastName());
		assertEquals("9182877790", user.getPhoneNumber());
	}

	@Test
	public void updateUserNotFoundTest() {
		user.setUserId(1000L);
		userEntity.setLastName("setti");
		Mockito.when(userRepository.getOne(Mockito.anyLong())).thenReturn(null);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<User> entity = new HttpEntity<>(user, headers);

		ResponseEntity<UserResponse> responseEntity = this.restTemplate
				.exchange("http://localhost:" + port + "/updateUser", HttpMethod.PUT, entity, UserResponse.class);
		assertEquals(404, responseEntity.getStatusCodeValue());
		assertEquals(ResponseStatus.FAILURE, responseEntity.getBody().getStatus());
		KeyValueMessage message = objectMapper.convertValue(responseEntity.getBody().getPayload(),
				KeyValueMessage.class);
		assertEquals(message.getKey(), "reason");
		assertEquals(message.getMessage(), "User Not Found");
	}

	@Test
	public void deleteUserTest() {
		Mockito.when(userRepository.getOne(Mockito.anyLong())).thenReturn(userEntity);
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<?> entity = new HttpEntity<>(headers);

		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl("http://localhost:" + port + "/deleteUser/{userId}").queryParam("userId", 1000L);
		ResponseEntity<UserResponse> responseEntity = this.restTemplate.exchange(builder.toUriString(),
				HttpMethod.DELETE, entity, UserResponse.class);
		assertEquals(200, responseEntity.getStatusCodeValue());
		assertEquals(ResponseStatus.SUCCESS, responseEntity.getBody().getStatus());
		KeyValueMessage message = objectMapper.convertValue(responseEntity.getBody().getPayload(),
				KeyValueMessage.class);
		assertEquals(message.getKey(), "message");
		assertEquals(message.getMessage(), "User has been deleted succesfully");
	}

	@Test
	public void deleteUserNotFoundTest() {
		Mockito.when(userRepository.getOne(Mockito.anyLong())).thenReturn(null);
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<?> entity = new HttpEntity<>(headers);

		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl("http://localhost:" + port + "/deleteUser/{userId}").queryParam("userId", 1000L);
		ResponseEntity<UserResponse> responseEntity = this.restTemplate.exchange(builder.toUriString(),
				HttpMethod.DELETE, entity, UserResponse.class);
		assertEquals(404, responseEntity.getStatusCodeValue());
		assertEquals(ResponseStatus.FAILURE, responseEntity.getBody().getStatus());
		KeyValueMessage message = objectMapper.convertValue(responseEntity.getBody().getPayload(),
				KeyValueMessage.class);
		assertEquals(message.getKey(), "reason");
		assertEquals(message.getMessage(), "User Not Found");
	}
}
