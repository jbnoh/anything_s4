package com.anything.api.login.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anything.api.login.service.LoginService;
import com.anything.dto.login.LoginParamDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/login")
public class LoginController {

	private final LoginService loginService;

	@PostMapping
	public ResponseEntity<?> login(@RequestBody LoginParamDTO param) throws Exception {

		return loginService.login(param);
	}

	@PostMapping("/refresh")
	public ResponseEntity<?> refresh(HttpServletRequest req) {

		return loginService.refresh(req);
	}
}
