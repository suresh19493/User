package com.test.user.enums;

public enum ResponseStatus {
	SUCCESS("sucess"), FAILURE("failure");
	private final String status;

	ResponseStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
}
