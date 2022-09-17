package com.anything.jpa.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anything.jpa.user.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

	public Optional<UserEntity> findByUserId(String userId);
}
