package com.test.user.model;

public class KeyValueMessage {
	private String key;
	private String message;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ErrorModel [key=").append(key).append(", message=").append(message).append("]");
		return builder.toString();
	}

}
