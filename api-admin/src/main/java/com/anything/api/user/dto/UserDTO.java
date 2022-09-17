package com.anything.api.user.dto;

import java.sql.Timestamp;

import com.anything.type.impl.UserType;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

	private Long seq;
	private String userId;
	private String userName;
	private UserType userType;
	private Timestamp createdDate;
	private Timestamp modifiedDate;

	private Long organSeq;
	private String organName;
}
