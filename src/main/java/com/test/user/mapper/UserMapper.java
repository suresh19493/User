package com.test.user.mapper;

import javax.validation.Valid;

import com.test.user.entity.UserEntity;
import com.test.user.model.User;

public interface UserMapper {

	UserEntity getUserEntity(@Valid User user);

	User getUser(UserEntity userEntity);

}
