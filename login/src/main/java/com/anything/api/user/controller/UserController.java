package com.anything.api.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anything.api.user.service.UserService;
import com.anything.dto.user.UserParamDTO;
import com.anything.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

	private final UserService userService;

	@PostMapping("/save")
	public ResponseEntity<?> save(@RequestBody UserParamDTO param) {

		userService.save(param);

		ApiResponse response = new ApiResponse();
		return new ResponseEntity<>(response, response.getStatus());
	}
}
