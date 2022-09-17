package com.anything.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@ConfigurationProperties("app.apicall.login")
public class ApicallLoginProp {

	private String userSave;
	private String login;
	private String tokenRefresh;
	private String tokenCheck;
}
