package com.anything.response;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {

	private HttpStatus status;

	private int code;

	private String message;

	private Object body;

	public ApiResponse() {

		this(HttpStatus.OK);
	}

	public ApiResponse(HttpStatus status) {

		if (status == null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		this.status = status;
		this.code = status.value();
	}

	public ApiResponse(HttpStatus status, Object body) {

		this(status);
		this.body = body;
	}

	public ApiResponse(HttpStatus status, String message, Object body) {

		this(status, body);
		this.message = message;
	}
}
