package com.anything.dto.login;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginParamDTO {

	@ApiModelProperty(value = "아이디")
	private String userId;
	@ApiModelProperty(value = "패스워드")
	private String userPw;
}
