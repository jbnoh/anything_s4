package com.anything.api.organ.dto;

import com.anything.type.impl.StatusType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrganParamDTO {

	@ApiModelProperty(value = "Primary key\n(처음 생성시 제외)")
	private long seq;
	@ApiModelProperty(value = "조직명")
	private String organName;
	@ApiModelProperty(value = "상태\n(A=ACTIVE, I=INACTIVE)")
	private StatusType status;
}
