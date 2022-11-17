package com.anything.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@ConfigurationProperties("app.async")
public class AsyncProp {

	private int corePoolSize;
	private int maxPoolSize;
	private int queueCapacity;
}
