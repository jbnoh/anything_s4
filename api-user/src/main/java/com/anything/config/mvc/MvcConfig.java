package com.anything.config.mvc;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.anything.interceptor.AuthInterceptor;
import com.anything.type.DefaultType.JwtType;
import com.anything.type.converter.StringTypeParameterConverter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class MvcConfig implements WebMvcConfigurer {

	private final AuthInterceptor authInterceptor;

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
	public void addCorsMappings(CorsRegistry registry) {

		registry.addMapping("/**")
				.allowedOrigins("*")
				.allowedMethods("GET", "POST", "PUT", "DELETE")
				.allowedHeaders("Origin", "X-Requested-With", "Content-Type", "Accept", "Authorization", JwtType.TOKEN.getValue())
				.maxAge(3600);
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {

		registry.addConverterFactory(new StringTypeParameterConverter());
	}
}
