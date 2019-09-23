package com.test.user.service;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.test.user.entity.UserEntity;
import com.test.user.exception.UserAlreadyExistsException;
import com.test.user.exception.UserNotFoundException;
import com.test.user.mapper.UserMapper;
import com.test.user.model.User;
import com.test.user.repository.UserRepository;

@Component
public class UserServiceImpl implements UserService {
	private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	private UserRepository userRepository;
	private UserMapper userMapper;

	@Autowired
	UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
		this.userRepository = userRepository;
		this.userMapper = userMapper;
	}

	@Override
	public UserEntity addUser(@Valid final User user) throws UserAlreadyExistsException {
		logger.debug("User Details are "+ user);
		try {
			if (getUserByPhoneNumber(user.getPhoneNumber()) == null) {
				UserEntity userEntity = userMapper.getUserEntity(user);
				userEntity = userRepository.save(userEntity);
				return userEntity;
			} else {
				throw new UserAlreadyExistsException("User Already Exists");
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
			throw e;
		}
	}

	@Override
	public UserEntity getUser(@NotNull final Long userId) throws UserNotFoundException {
		logger.debug("User id :"+ userId);

		try {
			UserEntity userEntity = userRepository.getOne(userId);
			if (userEntity == null) {
				throw new UserNotFoundException("User Not Found");
			}
			return userEntity;
		} catch (Exception e) {
			logger.error(e.toString(), e);
			throw e;
		}
	}

	@Override
	public List<UserEntity> getAllUsers() {
		try {
			List<UserEntity> userEntityList = userRepository.findAll();
			logger.debug("User list : {}", userEntityList);
			return userEntityList;
		} catch (Exception e) {
			logger.error(e.toString(), e);
			throw e;
		}
	}

	@Override
	public UserEntity updateUser(@Valid final User user) throws UserNotFoundException {
		logger.debug("User deatils are:"+ user);

		try {
			UserEntity userEntity = getUser(user.getUserId());
			if (userEntity != null) {
				userEntity = userMapper.getUserEntity(user);
				userEntity = userRepository.save(userEntity);
			} else {
				throw new UserNotFoundException("User Not Found");
			}
			return userEntity;
		} catch (Exception e) {
			logger.error(e.toString(), e);
			throw e;
		}
	}

	@Override
	public boolean deleteUser(@NotNull final Long userId) throws UserNotFoundException {
		logger.debug("User id:"+ userId);

		try {
			UserEntity userEntity = getUser(userId);
			if (userEntity != null) {
				userRepository.delete(userEntity);
				return true;
			} else {
				throw new UserNotFoundException("User Not Found");
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
			throw e;
		}
	}

	@Override
	public UserEntity getUserByPhoneNumber(@NotNull String phoneNumber) {
		try {
			UserEntity userEntity = userRepository.getUserByPhoneNumber(phoneNumber);
			logger.debug("get user details by phone number:"+ phoneNumber +" are "+userEntity);
			return userEntity;
		} catch (Exception e) {
			logger.error(e.toString(), e);
			throw e;
		}
	}

}
