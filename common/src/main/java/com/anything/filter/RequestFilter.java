package com.anything.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import com.anything.filter.request.RereadableRequestWrapper;

/**
 * RequestUtil.java getParams() 에서
 * body 로 요청 받은 데이터를 로깅 용도로 표출하기 위해 만들었으나 아래와 같이 오류 발생
 * 
 * java.lang.IllegalStateException: getInputStream() has already been called for this request
 * 
 * 오류 원인은... 최초 컨트롤러에서 body 를 꺼내오기 위해 이미 inputStream 을 읽은 상태인데, 또 읽으려고 하여...
 *  (즉, HttpServletRequest inputStream 의 경우 한번 읽으면 다시 읽을 수 없음 (톰캣 자체에서 막아놈)
 * 
 * 결론은... 처리할 수 있는 방법 구글링하여 적용함
 */
@WebFilter("/*")
public class RequestFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		RereadableRequestWrapper rereadableRequestWrapper = new RereadableRequestWrapper((HttpServletRequest) request);
		chain.doFilter(rereadableRequestWrapper, response);
	}
}
