package com.anything.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class DefaultType {

	@Getter
	@RequiredArgsConstructor
	public enum JwtType {
		TOKEN("token");

		private final String value;
	}

	@Getter
	@RequiredArgsConstructor
	public enum ServiceType {
		ADMIN("admin"),
		USER("user");

		private final String value;
	}
}
