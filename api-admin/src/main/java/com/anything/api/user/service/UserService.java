package com.anything.api.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.anything.api.user.dto.UserDTO;
import com.anything.dto.user.UserParamDTO;
import com.anything.exception.CustomException;
import com.anything.jpa.user.entity.UserEntity;
import com.anything.jpa.user.repository.UserRepository;
import com.anything.properties.ApicallLoginProp;
import com.anything.response.ApiResponse;
import com.anything.service.ApiService;
import com.anything.type.DefaultType.JwtType;
import com.anything.type.impl.UserType;
import com.anything.utils.BeanUtil;
import com.anything.utils.HttpHeaderUtil;
import com.anything.vo.DataMap;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final ApicallLoginProp apicallLoginProp;

	private final ApiService<ApiResponse> apiService;

	private final UserRepository userRepository;

	/**
	 * 사용자 뷰
	 * 
	 * @param seq
	 * @return
	 */
	public ResponseEntity<?> view(long seq) {

		UserDTO result = new UserDTO();

		Optional<UserEntity> opUser = userRepository.findById(seq);
		if (opUser.isPresent()) {
			UserEntity user = opUser.get();
			BeanUtil.copyProperties(user, result);
			result.setOrganSeq(user.getOrganEntity().getSeq());
		}

		ApiResponse response = new ApiResponse(HttpStatus.OK, result);
		return new ResponseEntity<>(response, response.getStatus());
	}

	/**
	 * 사용자 리스트
	 * 
	 * @param pageable
	 * @return
	 */
	public ResponseEntity<?> list(Pageable pageable) {

		List<UserDTO> result = new ArrayList<>();

		List<UserEntity> userList = userRepository.findAll(pageable).getContent();
		if (userList != null) {
			userList.forEach(u -> {
				UserDTO user = new UserDTO();
				BeanUtil.copyProperties(u, user);
				user.setOrganName(u.getOrganEntity().getOrganName());

				result.add(user);
			});
		}

		ApiResponse response = new ApiResponse(HttpStatus.OK, result);
		return new ResponseEntity<>(response, response.getStatus());
	}

	/**
	 * 사용자 생성(업데이트)
	 * 
	 * @param req
	 * @param userParamDTO
	 */
	public void save(HttpServletRequest req, UserParamDTO userParamDTO) {

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

		ResponseEntity<?> response = apiService.post(apicallLoginProp.getUserSave(), headers, userParamDTO);
		if (HttpStatus.OK != response.getStatusCode()) {
			throw new CustomException(
					String.format("API call failed [URL: %s]", apicallLoginProp.getUserSave()),
					response.getBody(),
					response.getStatusCode());
		}
	}

	/**
	 * 사용자 삭제
	 * 
	 * @param seq
	 */
	public void delete(long seq) {

		Optional<UserEntity> opUser = userRepository.findById(seq);
		if (opUser.isPresent()) {
			UserEntity user = opUser.get();
			if (UserType.ADMIN == user.getUserType()) {
				throw new CustomException(
						String.format("User type is admin [USER: %s]", user.getUserId()));
			}

			userRepository.deleteById(user.getSeq());
		}
	}
}
