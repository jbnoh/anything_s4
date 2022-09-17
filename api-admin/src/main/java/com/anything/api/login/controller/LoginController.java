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

/**
 * @UserMapping(type = {UserType.SUPER_ADMIN, UserType.COMPANY_ADMIN})
 * 
 * 로그인의 경우, AOP BEFORE 단계에서 user 정보가 없기 때문에 loginService 내에서 관리자 여부를 처리함
 */
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
	public ResponseEntity<?> refresh(HttpServletRequest req) throws Exception {

		return loginService.refresh(req);
	}
}
