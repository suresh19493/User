package com.test.user.model;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class User {
	@ApiModelProperty(name = "userId", dataType = "long", required = false)
	@JsonProperty(value = "userId", required = false)
	@NotNull(message = "First name is required")
	private Long userId;
	@ApiModelProperty(name = "firstName", dataType = "string", required = true)
	@JsonProperty(value = "firstName", required = true)
	private String firstName;
	@ApiModelProperty(name = "lastName", dataType = "string", required = true)
	@JsonProperty(value = "lastName", required = true)
	@NotNull(message = "Last name is required")
	private String lastName;
	@ApiModelProperty(name = "phoneNumber", dataType = "string", required = true)
	@JsonProperty(value = "phoneNumber", required = true)
	@NotNull(message = "Phone Number is required")
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
		builder.append("User [userId=").append(userId).append(", firstName=").append(firstName).append(", lastName=")
				.append(lastName).append(", phoneNumber=").append(phoneNumber).append("]");
		return builder.toString();
	}
}
