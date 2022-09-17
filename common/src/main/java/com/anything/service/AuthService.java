package com.anything.service;

import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.anything.exception.CustomException;
import com.anything.jpa.user.entity.UserEntity;
import com.anything.jpa.user.repository.UserRepository;
import com.anything.type.DefaultType.JwtType;
import com.anything.utils.HttpHeaderUtil;
import com.anything.vo.DataMap;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final ApiService<?> apiService;

	private final UserRepository userRepository;

	public UserEntity getUserEntity(HttpServletRequest req, String tokenCheckUrl) {

		String token = req.getHeader(JwtType.TOKEN.getValue());
		if (StringUtils.isBlank(token)) {
			throw new CustomException("token is null", HttpStatus.NOT_FOUND);
		}

		DataMap dataMap = new DataMap();
		dataMap.put(JwtType.TOKEN.getValue(), token);

		HttpHeaders headers = HttpHeaderUtil.builder()
				.add(dataMap)
				.build();

		ResponseEntity<?> response = apiService.get(tokenCheckUrl, headers);

		if (HttpStatus.OK == response.getStatusCode()) {
			Object obj = response.getBody();
			DataMap result = new DataMap();
			result.addAll((Map) obj);

			Optional<UserEntity> opUser = userRepository.findByUserId(result.getString("body"));
			if (opUser.isPresent()) {
				return opUser.get();
			}
		}

		return null;
	}
}
