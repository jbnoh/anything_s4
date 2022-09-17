package com.anything.api.login.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.anything.dto.login.LoginParamDTO;
import com.anything.exception.CustomException;
import com.anything.properties.ApicallLoginProp;
import com.anything.response.ApiResponse;
import com.anything.service.ApiService;
import com.anything.type.DefaultType.JwtType;
import com.anything.utils.HttpHeaderUtil;
import com.anything.vo.DataMap;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginService {

	private final ApicallLoginProp apicallLoginProp;

	private final ApiService<ApiResponse> apiService;

	/**
	 * 로그인
	 * 
	 * @param loginParamDto
	 * @return
	 */
	public ResponseEntity<?> login(LoginParamDTO loginParamDto) {

		HttpHeaders headers = HttpHeaderUtil.builder()
				.contentType(MediaType.APPLICATION_JSON)
				.build();

		return apiService.post(apicallLoginProp.getLogin(), headers, loginParamDto);
	}

	/**
	 * 토큰 재발급
	 * 
	 * @param req
	 * @return
	 */
	public ResponseEntity<?> refresh(HttpServletRequest req) {

		String token = req.getHeader(JwtType.TOKEN.getValue());
		if (StringUtils.isBlank(token)) {
			throw new CustomException("token is null", HttpStatus.NOT_FOUND);
		}

		DataMap dataMap = new DataMap();
		dataMap.put(JwtType.TOKEN.getValue(), token);

		HttpHeaders headers = HttpHeaderUtil.builder()
				.contentType(MediaType.APPLICATION_JSON)
				.add(dataMap)
				.build();

		return apiService.get(apicallLoginProp.getTokenRefresh(), headers);
	}
}
