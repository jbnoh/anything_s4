package com.anything.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import com.anything.exception.CustomException;
import com.anything.jpa.user.entity.UserEntity;
import com.anything.properties.ApicallLoginProp;
import com.anything.service.AuthService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

	private final ApicallLoginProp apicallLoginProp;

	private final AuthService authService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		UserEntity userEntity = authService.getUserEntity(request, apicallLoginProp.getTokenCheck());

		if (ObjectUtils.isEmpty(userEntity)) {
			throw new CustomException("User is empty");
		}

		request.setAttribute("userEntity", userEntity);
		return true;
	}
}
