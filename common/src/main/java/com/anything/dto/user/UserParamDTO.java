package com.anything.dto.user;

import com.anything.type.impl.UserType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserParamDTO {

	@ApiModelProperty(value = "Primary key\n(처음 생성시 제외)")
	private long seq;
	@ApiModelProperty(value = "관리자/사용자 구분\n(A=ADMIN(관리자), U=USER(일반사용자))")
	private UserType userType;
	@ApiModelProperty(value = "아이디")
	private String userId;
	@ApiModelProperty(value = "패스워드")
	private String userPw;
	@ApiModelProperty(value = "이름")
	private String userName;

	@ApiModelProperty(value = "조직 Primary key")
	private long organSeq;
}
