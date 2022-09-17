package com.anything.api.login.service;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.anything.dto.login.LoginDTO;
import com.anything.jpa.user.entity.UserEntity;
import com.anything.jpa.user.entity.embeddable.UserAuthEm;
import com.anything.jpa.user.repository.UserRepository;
import com.anything.jwt.JwtProvider;
import com.anything.jwt.dto.JwtDTO;
import com.anything.jwt.dto.TokenValueDTO;
import com.anything.jwt.user.UserDetail;
import com.anything.vo.DataMap;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginService {

	private final UserRepository userRepository;

	private final JwtProvider jwtProvider;

	private final AuthenticationManager authenticationManager;

	public String getAuthenticationByUserId() {

		UserDetail detail = (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return detail.getUsername(); 
	}

	public JwtDTO login(LoginDTO loginDto) throws Exception {

		Authentication auth = null;

		try {
			auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUserId(), loginDto.getUserPw()));

		} catch (AuthenticationException e) {
			throw e;
		}

		UserDetail user = (UserDetail) auth.getPrincipal();
		String userId = user.getUsername();

		TokenValueDTO tokenValDto = TokenValueDTO.builder()
				.userId(userId)
				.build();

		return createTokenReturn(tokenValDto);
	}

	public JwtDTO refresh() throws Exception {

		String userId = this.getAuthenticationByUserId();

		TokenValueDTO tokenValDto = TokenValueDTO.builder()
				.userId(userId)
				.build();

		return createTokenReturn(tokenValDto);
	}

	private JwtDTO createTokenReturn(TokenValueDTO tokenValDto) throws Exception {

		String accessToken = jwtProvider.createAccessToken(tokenValDto);

		DataMap refresh = jwtProvider.createRefreshToken(tokenValDto);
		String refreshToken = refresh.getString("refreshToken");
		Long refreshExpire = refresh.getLong("refreshTokenExpire");

		String userId = tokenValDto.getUserId();

		Optional<UserEntity> opUser = userRepository.findByUserId(userId);
		if (opUser.isPresent()) {
			UserEntity user = opUser.get();

			user.getUserAuthEm().clear();

			user.getUserAuthEm().add(
					UserAuthEm.builder()
						.accessToken(accessToken)
						.refreshToken(refreshToken)
						.refreshTokenExpire(refreshExpire)
						.build()
			);

			return JwtDTO.builder()
					.accessToken(accessToken)
					.refreshToken(refreshToken)
					.refreshExpire(refreshExpire)
					.userType(user.getUserType())
					.build();
		}

		return null;
	}
}
