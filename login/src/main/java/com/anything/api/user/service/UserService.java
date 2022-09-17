package com.anything.api.user.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.anything.dto.user.UserParamDTO;
import com.anything.exception.CustomException;
import com.anything.jpa.organ.entity.OrganEntity;
import com.anything.jpa.organ.repository.OrganRepository;
import com.anything.jpa.user.entity.UserEntity;
import com.anything.jpa.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	private final OrganRepository organRepository;

	private final PasswordEncoder passwordEncoder;

	/**
	 * 사용자 생성(업데이트)
	 * 
	 * @param userParamDTO
	 * @return
	 */
	public void save(UserParamDTO userParamDTO) {

		Optional<UserEntity> opUser = userRepository.findById(userParamDTO.getSeq());
		Optional<OrganEntity> opOrgan = organRepository.findById(userParamDTO.getOrganSeq());

		if (opUser.isPresent()) {
			UserEntity user = opUser.get();
			user.updateUserPw(passwordEncoder.encode(userParamDTO.getUserPw()));
			user.updateUserName(userParamDTO.getUserName());
			user.updateUserType(userParamDTO.getUserType());

			if (opOrgan.isPresent()) {
				user.updateOrganEntity(opOrgan.get());
			}
		} else {
			if (opOrgan.isEmpty()) {
				throw new CustomException("Organ is null");
			}

			UserEntity user = UserEntity.builder()
					.userId(userParamDTO.getUserId())
					.userPw(passwordEncoder.encode(userParamDTO.getUserPw()))
					.userName(userParamDTO.getUserName())
					.userType(userParamDTO.getUserType())
					.organEntity(opOrgan.get())
					.build();

			userRepository.save(user);
		}
	}
}
