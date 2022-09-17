package com.anything.api.organ.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.anything.api.organ.dto.OrganDTO;
import com.anything.api.organ.dto.OrganParamDTO;
import com.anything.exception.CustomException;
import com.anything.jpa.organ.entity.OrganEntity;
import com.anything.jpa.organ.repository.OrganRepository;
import com.anything.response.ApiResponse;
import com.anything.type.impl.StatusType;
import com.anything.type.impl.UserType;
import com.anything.utils.BeanUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrganService {

	private final OrganRepository organRepository;

	/**
	 * ORGAN 뷰
	 * 
	 * @param seq
	 * @return
	 */
	public ResponseEntity<?> view(long seq) {

		OrganDTO result = new OrganDTO();

		Optional<OrganEntity> opOrgan = organRepository.findById(seq);
		if (opOrgan.isPresent()) {
			BeanUtil.copyProperties(opOrgan.get(), result);
		}

		ApiResponse response = new ApiResponse(HttpStatus.OK, result);
		return new ResponseEntity<>(response, response.getStatus());
	}

	/**
	 * ORGAN 리스트
	 * 
	 * @param pageable
	 * @return
	 */
	public ResponseEntity<?> list(Pageable pageable) {

		List<OrganDTO> result = new ArrayList<>();

		List<OrganEntity> organList = organRepository.findAll(pageable).getContent();
		organList.forEach(o -> {
			OrganDTO organ = new OrganDTO();
			BeanUtil.copyProperties(o, organ);
			result.add(organ);
		});

		ApiResponse response = new ApiResponse(HttpStatus.OK, result);
		return new ResponseEntity<>(response, response.getStatus());
	}

	/**
	 * ORGAN 생성(업데이트)
	 * 
	 * @param organParamDTO
	 */
	public void save(OrganParamDTO organParamDTO) {

		Optional<OrganEntity> organ = organRepository.findById(organParamDTO.getSeq());

		if (organ.isPresent()) {
			OrganEntity oEntity = organ.get();
			oEntity.updateOrganName(organParamDTO.getOrganName());
			oEntity.updateStatus(organParamDTO.getStatus());
		} else {
			OrganEntity entity = OrganEntity.builder()
					.organName(organParamDTO.getOrganName())
					.status(organParamDTO.getStatus())
					.build();

			organRepository.save(entity);
		}
	}

	/**
	 * ORGAN 삭제
	 * 
	 * @param seq
	 */
	public void delete(long seq) {

		Optional<OrganEntity> opOrgan = organRepository.findById(seq);
		if (opOrgan.isPresent()) {
			OrganEntity organ = opOrgan.get();
			if (StatusType.ACTIVE == organ.getStatus()) {
				throw new CustomException(
						String.format("Organ is active [ORGAN: %s]", organ.getOrganName()));
			}

			/*
			if (organ.getUserEntity().size() > 0) {
				throw new CustomException("Users belonging to the organ exist");
			}
			*/
			organ.getUserEntity().forEach(u -> {
				if (UserType.ADMIN == u.getUserType()) {
					throw new CustomException("Admin belonging to the organ exist");
				}
			});

			organRepository.deleteById(organ.getSeq());
		}
	}
}
