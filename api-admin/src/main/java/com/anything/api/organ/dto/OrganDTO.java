package com.anything.api.organ.dto;

import com.anything.type.impl.StatusType;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrganDTO {

	private Long seq;
	private String organName;
	private StatusType status;
}
