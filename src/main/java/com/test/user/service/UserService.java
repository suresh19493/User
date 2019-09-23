package com.test.user.service;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.test.user.entity.UserEntity;
import com.test.user.exception.UserAlreadyExistsException;
import com.test.user.exception.UserNotFoundException;
import com.test.user.model.User;

public interface UserService {

	UserEntity addUser(@Valid User user) throws UserAlreadyExistsException;

	UserEntity getUser(@NotNull Long userId) throws UserNotFoundException;

	List<UserEntity> getAllUsers();

	UserEntity updateUser(@Valid User user) throws UserNotFoundException;

	boolean deleteUser(@NotNull Long userId) throws UserNotFoundException;

	UserEntity getUserByPhoneNumber(@NotNull String phoneNumber);

}
