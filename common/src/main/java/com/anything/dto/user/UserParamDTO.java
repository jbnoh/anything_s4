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
	@ApiModelProperty(value = "관리자/사용자 구분\n(S=SUPER_ADMIN(최고관리자), C=COMPANY_ADMIN(기업관리자), U=USER(일반사용자))")
	private UserType userType;
	private String userId;
	private String userPw;
	private String userName;

	private long organSeq;
}
