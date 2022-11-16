package com.anything.config.mvc;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.anything.bind.DataArgumentResolver;
import com.anything.interceptor.AuthInterceptor;
import com.anything.type.converter.StringTypeParameterConverter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class MvcConfig implements WebMvcConfigurer {

	private final AuthInterceptor authInterceptor;

	private final DataArgumentResolver dataResolver;

	private final String[] excludeSystem = {
		"/error",
		"/configuration/ui",
		"/configuration/security",
		"/v2/api-docs",
		"/swagger-resources/**",
		"/swagger-ui/**",
		"/webjars/**"
	};

	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		registry.addInterceptor(authInterceptor)
				.excludePathPatterns(excludeSystem)
				.excludePathPatterns("/api/v1/login/**");
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {

		registry.addConverterFactory(new StringTypeParameterConverter());
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {

		resolvers.add(dataResolver);
	}
}
