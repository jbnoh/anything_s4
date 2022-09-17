package com.anything.jwt.dto;

import com.anything.type.impl.UserType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtDTO {

	private String accessToken;

	private String refreshToken;

	private Long refreshExpire;

	private UserType userType;
}
