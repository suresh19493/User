package com.test.user.mapper;

import javax.validation.Valid;

import org.springframework.stereotype.Component;

import com.test.user.entity.UserEntity;
import com.test.user.model.User;

@Component
public class UserMapperImpl implements UserMapper {

	@Override
	public UserEntity getUserEntity(@Valid final User user) {
		UserEntity userEntity = new UserEntity();
		userEntity.setFirstName(user.getFirstName());
		userEntity.setLastName(user.getLastName());
		userEntity.setPhoneNumber(user.getPhoneNumber());
		return userEntity;
	}

	@Override
	public User getUser(UserEntity userEntity) {
		User user = new User();
		user.setFirstName(userEntity.getFirstName());
		user.setLastName(userEntity.getLastName());
		user.setPhoneNumber(userEntity.getPhoneNumber());
		user.setUserId(userEntity.getUserId());
		return user;
	
	}
	
}
