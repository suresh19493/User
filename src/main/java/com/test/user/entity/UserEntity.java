package com.test.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "USER_TBL")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "USER_TBL_SEQ")
	@Column(name = "userid")
	private Long userId;
	@Column(name = "firstname")
	private String firstName;
	@Column(name = "lastname")
	private String lastName;
	@Column(name = "phonenumber")
	private String phoneNumber;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserEntity [userId=").append(userId).append(", firstName=").append(firstName)
				.append(", lastName=").append(lastName).append(", phoneNumber=").append(phoneNumber).append("]");
		return builder.toString();
	}
}
