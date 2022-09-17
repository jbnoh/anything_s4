package com.anything.type.impl;

import javax.persistence.Converter;

import com.anything.type.CodeValue;
import com.anything.type.converter.StringTypeConverter;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StatusType implements CodeValue {

	ACTIVE("A", "Active"),
	IN_ACTIVE("I", "Inactive");

	private final String code;
	private final String value;
}

@Converter(autoApply = true)
class StatusTypeConverter extends StringTypeConverter<StatusType> {

	StatusTypeConverter() {
		super(StatusType.class);
	}
}
