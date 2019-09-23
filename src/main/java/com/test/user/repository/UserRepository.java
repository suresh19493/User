package com.test.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.test.user.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

	@Query(value = "SELECT * FROM USER_TBL WHERE PHONENUMBER=?1", nativeQuery = true)
	UserEntity getUserByPhoneNumber(String phoneNumber);
}
