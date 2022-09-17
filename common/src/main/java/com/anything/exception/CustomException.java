package com.anything.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

	private static final long serialVersionUID = 4012896895001799423L;

	private String resultMessage;

	private Object resultBody;

	private HttpStatus status;

	/**
	 * @param cause
	 */
	public CustomException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 */
	public CustomException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param status
	 */
	public CustomException(String message, HttpStatus status) {
		this(message);
		this.status = status;
	}
	/**
	 * @param message
	 * @param resultBody
	 */
	public CustomException(String message, Object resultBody) {
		this(message);
		this.resultBody = resultBody;
	}
	/**
	 * @param message
	 * @param resultBody
	 * @param status
	 */
	public CustomException(String message, Object resultBody, HttpStatus status) {
		this(message, resultBody);
		this.status = status;
	}

	/**
	 * @param message
	 * @param resultMessage
	 */
	public CustomException(String message, String resultMessage) {
		this(message);
		this.resultMessage = resultMessage;
	}
	/**
	 * @param message
	 * @param resultMessage
	 * @param status
	 */
	public CustomException(String message, String resultMessage, HttpStatus status) {
		this(message, resultMessage);
		this.status = status;
	}
	/**
	 * @param message
	 * @param resultMessage
	 * @param resultBody
	 */
	public CustomException(String message, String resultMessage, Object resultBody) {
		this(message, resultMessage);
		this.resultBody = resultBody;
	}
	/**
	 * @param message
	 * @param resultMessage
	 * @param resultBody
	 * @param status
	 */
	public CustomException(String message, String resultMessage, Object resultBody, HttpStatus status) {
		this(message, resultMessage, resultBody);
		this.status = status;
	}
}
