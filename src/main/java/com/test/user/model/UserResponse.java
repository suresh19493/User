package com.test.user.model;

import com.test.user.enums.ResponseStatus;

public class UserResponse {
	private int httpStatus;
	private ResponseStatus status;
	private Object payload;

	public int getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(int httpCode) {
		this.httpStatus = httpCode;
	}

	public ResponseStatus getStatus() {
		return status;
	}

	public void setStatus(ResponseStatus status) {
		this.status = status;
	}

	public Object getPayload() {
		return payload;
	}

	public void setPayload(Object payload) {
		this.payload = payload;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SuccessResponse [httpStatus=").append(httpStatus).append(", status=").append(status)
				.append(", payload=").append(payload).append("]");
		return builder.toString();
	}

}
