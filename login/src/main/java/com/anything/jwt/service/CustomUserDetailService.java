package com.anything.jwt.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.anything.jpa.user.entity.UserEntity;
import com.anything.jpa.user.repository.UserRepository;
import com.anything.jwt.user.UserDetail;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		return getUserDetail(username);
	}

	public UserDetail getUserDetail(String userId) {

		Optional<UserEntity> opUser = userRepository.findByUserId(userId);
		if (opUser.isPresent()) {
			UserEntity user = opUser.get();

			return UserDetail.builder()
					.username(user.getUserId())
					.password(user.getUserPw())
					.organEntity(user.getOrganEntity())
					.userType(user.getUserType())
					.build();
		}

		return null;
	}
}
