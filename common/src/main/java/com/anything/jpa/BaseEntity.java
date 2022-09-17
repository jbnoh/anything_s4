package com.anything.jpa;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;

@EntityListeners(AuditingEntityListener.class)
@Getter
@MappedSuperclass
public class BaseEntity {

	@CreatedDate
	@Column(name = "CREATED_DATE", updatable = false)
	private Timestamp createdDate;

	@LastModifiedDate
	@Column(name = "MODIFIED_DATE")
	private Timestamp modifiedDate;
}
