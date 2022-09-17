package com.anything.jpa.user.entity.embeddable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
@Getter
public class UserAuthEm {

	@Column(name = "ACCESS_TOKEN", length = 300, nullable = false)
	private String accessToken;

	@Column(name = "REFRESH_TOKEN", length = 300, nullable = false)
	private String refreshToken;

	@Column(name = "REFRESH_TOKEN_EXPIRE", nullable = false)
	private Long refreshTokenExpire;

	@Builder
	public UserAuthEm(
			@Size(max = 300) String accessToken, @Size(max = 300) String refreshToken, Long refreshTokenExpire) {

		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.refreshTokenExpire = refreshTokenExpire;
	}
}
