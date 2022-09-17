package com.anything.aop;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.anything.response.ApiResponse;
import com.anything.utils.RequestUtil;
import com.anything.vo.DataMap;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class LoggerAspect {

	@Around("within(com.anything.api..*) and "
			+ "@annotation(org.springframework.web.bind.annotation.GetMapping) or "
			+ "@annotation(org.springframework.web.bind.annotation.PostMapping) or "
			+ "@annotation(org.springframework.web.bind.annotation.PutMapping) or "
			+ "@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
	public Object logAction(ProceedingJoinPoint joinPoint) throws Throwable {

		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();

		String controllerName = joinPoint.getSignature().getDeclaringType().getSimpleName();
		String methodName = joinPoint.getSignature().getName();

		DataMap logMap = new DataMap();

		try {
			logMap.put("controller", controllerName);
			logMap.put("method", methodName);
			logMap.put("http_method", request.getMethod());
			logMap.put("request_uri", request.getRequestURI());
			logMap.put("params_contentType", request.getContentType());
			logMap.put("params", RequestUtil.getParams(request));
			log.info("REQUEST -> {}", logMap);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		Object proceed = joinPoint.proceed();

		logMap.clear();
		logMap.put("controller", controllerName);
		logMap.put("method", methodName);

		String result = "";
		ObjectMapper mapper = new ObjectMapper();
		try {
			result = mapper.writeValueAsString(((ResponseEntity<ApiResponse>) proceed).getBody());
		} catch (Exception e) {}
		logMap.put("result", result);
		log.info("RESPONSE -> {}", logMap);

		return proceed;
	}
}
