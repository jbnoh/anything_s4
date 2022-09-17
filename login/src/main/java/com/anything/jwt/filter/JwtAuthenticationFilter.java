package com.anything.jwt.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.anything.jwt.JwtProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

	private final JwtProvider jwtProvider;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

		try {
			String token = jwtProvider.resolveToken((HttpServletRequest) request);

			if (StringUtils.isNotBlank(token) && jwtProvider.validateJwtToken(token)) {
				Authentication authentication = jwtProvider.getAuthentication(token);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
			filterChain.doFilter(request, response);
		} catch (Exception e) {
			String message= e.getMessage();

			log.error(message, e);

			HttpServletResponse res = (HttpServletResponse) response;
			res.setContentType("application/json;charset=UTF-8");
			res.setCharacterEncoding("utf-8");
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			res.getWriter().print(message);
		}
	}
}
