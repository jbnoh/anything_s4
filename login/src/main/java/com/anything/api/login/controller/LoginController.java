package com.anything.api.login.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anything.api.login.service.LoginService;
import com.anything.dto.login.LoginParamDTO;
import com.anything.jwt.dto.JwtDTO;
import com.anything.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/login")
public class LoginController {

	private final LoginService loginService;

	@PostMapping
	public ResponseEntity<?> login(@RequestBody LoginParamDTO param) throws Exception {

		JwtDTO jwtDto = loginService.login(param);

		ApiResponse response = new ApiResponse(HttpStatus.OK, jwtDto);
		return new ResponseEntity<>(response, response.getStatus());
	}

	@GetMapping("/refresh")
	public ResponseEntity<?> refresh() throws Exception {

		JwtDTO jwtDto = loginService.refresh();

		ApiResponse response = new ApiResponse(HttpStatus.OK, jwtDto);
		return new ResponseEntity<>(response, response.getStatus());
	}

	@GetMapping("/token")
	public ResponseEntity<?> token() throws Exception {

		String userId = loginService.getAuthenticationByUserId();

		ApiResponse response = new ApiResponse(HttpStatus.OK, userId);
		return new ResponseEntity<>(response, response.getStatus());
	}
}
