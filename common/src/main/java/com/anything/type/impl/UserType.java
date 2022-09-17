package com.anything.type.impl;

import javax.persistence.Converter;

import com.anything.type.CodeValue;
import com.anything.type.converter.StringTypeConverter;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserType implements CodeValue {

	ADMIN("A", "ADMIN"),
	USER("U", "USER");

	private final String code;
	private final String value;

	public static UserType codeOf(String code) {

		if (ADMIN.getCode().equals(code)) {
			return ADMIN;
		} else if (USER.getCode().equals(code)) {
			return USER;
		}

		return null;
	}
}

@Converter(autoApply = true)
class UserTypeConverter extends StringTypeConverter<UserType> {

	UserTypeConverter() {
		super(UserType.class);
	}
}
