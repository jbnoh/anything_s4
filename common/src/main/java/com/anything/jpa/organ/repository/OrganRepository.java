package com.anything.jpa.organ.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anything.jpa.organ.entity.OrganEntity;
import com.anything.type.impl.StatusType;

public interface OrganRepository extends JpaRepository<OrganEntity, Long> {

	public Optional<OrganEntity> findBySeqAndStatus(Long seq, StatusType status);

	public List<OrganEntity> findByStatus(StatusType status);
}
