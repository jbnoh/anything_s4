package com.anything.exception.handler;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.anything.exception.CustomException;
import com.anything.response.ApiResponse;
import com.anything.utils.RequestUtil;
import com.anything.vo.DataMap;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handler(HttpServletRequest req, Exception e) {

		String message = e.getMessage();

		log.error(message, e);

		return resultResponse(req, HttpStatus.INTERNAL_SERVER_ERROR, message, null);
	}

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<?> handler(HttpServletRequest req, CustomException e) {

		String message = e.getResultMessage();
		if (StringUtils.isBlank(message)) {
			message = e.getMessage();
		}

		log.error(message, e);

		return resultResponse(req, e.getStatus(), message, e.getResultBody());
	}

	private ResponseEntity<?> resultResponse(HttpServletRequest req, HttpStatus status, String message, Object resultObj) {

		DataMap body = new DataMap();
		body.put("params", RequestUtil.getParams(req));

		if (resultObj != null) {
			body.put("result", resultObj);
		}

		ApiResponse response = new ApiResponse(status, message, body);
		return new ResponseEntity<>(response, response.getStatus());
	}
}
