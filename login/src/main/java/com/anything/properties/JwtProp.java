package com.anything.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@ConfigurationProperties("jwt")
public class JwtProp {

	private String secretKey;
	private int accessExpire;
	private int refreshExpire;
}
