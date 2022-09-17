package com.anything.aop;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.anything.annotation.UserMapping;
import com.anything.exception.CustomException;
import com.anything.jpa.user.entity.UserEntity;
import com.anything.type.impl.UserType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class UserMappingCheckAspect {

	@Before("execution(public * *(..)) && within(@com.anything.annotation.UserMapping *)")
	public void userMappingCheck(JoinPoint joinPoint) throws Throwable {

		log.debug(this.getClass().getSimpleName() + " - userMappingCheck()");

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		Object user = request.getAttribute("userEntity");

		UserEntity userEntity;
		if (user instanceof UserEntity) {
			userEntity = (UserEntity) user;
		} else {
			throw new CustomException("User is empty");
		}

		UserType userType = userEntity.getUserType();

		UserMapping userMapping = (UserMapping) joinPoint.getSignature().getDeclaringType().getAnnotation(UserMapping.class);

		boolean is = false;
		for (UserType type : userMapping.type()) {
			if (userType == type) {
				is = true;
				break;
			}
		}

		if (!is) {
			throw new CustomException(String.format("Is not admin [USER_ID: %s]", userEntity.getUserId()));
		}
	}
}
