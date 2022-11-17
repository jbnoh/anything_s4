package com.anything.config.async;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.anything.properties.AsyncProp;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableAsync
public class AsyncConfig extends AsyncConfigurerSupport {

	private final AsyncProp asyncProp;

	@Override
	public Executor getAsyncExecutor() {

		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(asyncProp.getCorePoolSize());
		executor.setMaxPoolSize(asyncProp.getMaxPoolSize());
		executor.setQueueCapacity(asyncProp.getQueueCapacity());
		executor.setThreadNamePrefix("Executor-");
		executor.initialize();

		return executor;
	}
}
