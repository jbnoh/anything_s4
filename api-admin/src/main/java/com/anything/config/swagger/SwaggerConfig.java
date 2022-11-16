package com.anything.config.swagger;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.anything.swagger.rule.Page;
import com.fasterxml.classmate.TypeResolver;

import lombok.RequiredArgsConstructor;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@EnableWebMvc
@RequiredArgsConstructor
public class SwaggerConfig {

	private final TypeResolver typeResolver;

	@Bean
	public Docket swaggerApi() {

		return new Docket(DocumentationType.SWAGGER_2)
				.alternateTypeRules(
						AlternateTypeRules.newRule(typeResolver.resolve(Pageable.class), typeResolver.resolve(Page.class))
				)
				.consumes(getConsumeContentTypes())
				.produces(getProduceContentTypes())
				.apiInfo(swaggerInfo())
				.securityContexts(Arrays.asList(securityContext()))
				.securitySchemes(Arrays.asList(apiKey()))
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.anything"))
				.paths(PathSelectors.any())
				.build()
				.useDefaultResponseMessages(false);
	}

	private ApiInfo swaggerInfo() {

		return new ApiInfoBuilder()
				.title("ADMIN API")
				.description("Docs")
				.version("1.0.0")
				.build();
	}

	private Set<String> getConsumeContentTypes() {

		Set<String> consumes = new HashSet<>();
		consumes.add("application/json; charset=UTF-8");
		consumes.add("application/x-www-form-urlencoded");
		return consumes;
	}

	private Set<String> getProduceContentTypes() {

		Set<String> produces = new HashSet<>();
		produces.add("application/json; charset=UTF-8");
		return produces;
	}

	private ApiKey apiKey() {

		return new ApiKey("JWT", "token", "header");
	}

	private SecurityContext securityContext() {

		return SecurityContext.builder().securityReferences(defaultAuth()).build();
	}

	private List<SecurityReference> defaultAuth() {

		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
	}
}
