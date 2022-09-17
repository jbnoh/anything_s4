package com.anything.jpa.organ.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anything.jpa.organ.entity.OrganEntity;

public interface OrganRepository extends JpaRepository<OrganEntity, Long> {

}
