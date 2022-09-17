package com.anything.api.login.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.anything.dto.login.LoginDTO;
import com.anything.exception.CustomException;
import com.anything.properties.ApicallLoginProp;
import com.anything.response.ApiResponse;
import com.anything.service.ApiService;
import com.anything.type.DefaultType.JwtType;
import com.anything.type.impl.UserType;
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
	 * @param loginDto
	 * @return
	 * @throws Exception
	 */
	public ResponseEntity<?> login(LoginDTO loginDto) throws Exception {

		HttpHeaders headers = HttpHeaderUtil.builder()
				.contentType(MediaType.APPLICATION_JSON)
				.build();

		ResponseEntity<?> response = apiService.post(apicallLoginProp.getLogin(), headers, loginDto);
		adminCheck(response, loginDto.getUserId());

		return response;
	}

	/**
	 * 토큰 재발급
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public ResponseEntity<?> refresh(HttpServletRequest req) throws Exception {

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

		ResponseEntity<?> response = apiService.get(apicallLoginProp.getTokenRefresh(), headers);
		adminCheck(response, null);

		return response;
	}

	private void adminCheck(ResponseEntity response, String userId) throws Exception {

		DataMap resMap = new DataMap();
		resMap.addAll((Map) response.getBody());

		if (resMap.containsKey("body")) {
			DataMap body = new DataMap();
			body.addAll((Map) resMap.get("body"));

			UserType type = UserType.codeOf(body.getString("userType"));
			if (UserType.USER == type) {
				throw new CustomException(String.format("Is not admin [USER_ID: %s]", userId));
			}
		} else {
			throw new CustomException("Body is null");
		}
	}
}
