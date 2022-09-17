package com.anything.type;

import com.fasterxml.jackson.annotation.JsonValue;

public interface CodeValue {

	@JsonValue
	String getCode();

	String getValue();
}
