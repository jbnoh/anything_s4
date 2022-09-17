package com.anything.api.user.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anything.annotation.UserMapping;
import com.anything.api.user.service.UserService;
import com.anything.dto.user.UserParamDTO;
import com.anything.response.ApiResponse;
import com.anything.type.impl.UserType;

import lombok.RequiredArgsConstructor;

@UserMapping(type = UserType.ADMIN)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

	private final UserService userService;

	@GetMapping("/view/{seq}")
	public ResponseEntity<?> view(@PathVariable long seq) {

		return userService.view(seq);
	}

	@GetMapping("/list")
	public ResponseEntity<?> list(@PageableDefault(size = 10) final Pageable pageable) {

		return userService.list(pageable);
	}

	@PostMapping("/save")
	public ResponseEntity<?> save(HttpServletRequest req, @RequestBody UserParamDTO param) {

		userService.save(req, param);

		ApiResponse response = new ApiResponse();
		return new ResponseEntity<>(response, response.getStatus());
	}

	@DeleteMapping("/delete/{seq}")
	public ResponseEntity<?> delete(@PathVariable long seq) {

		userService.delete(seq);

		ApiResponse response = new ApiResponse();
		return new ResponseEntity<>(response, response.getStatus());
	}
}
