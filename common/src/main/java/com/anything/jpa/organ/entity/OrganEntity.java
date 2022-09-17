package com.anything.jpa.organ.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

import com.anything.jpa.BaseEntity;
import com.anything.jpa.user.entity.UserEntity;
import com.anything.type.impl.StatusType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(
	name = "A_ORGAN",
	uniqueConstraints = {
		@UniqueConstraint(columnNames = {"ORGAN_NAME"})
	}
)
public class OrganEntity extends BaseEntity {

	@Column(name = "SEQ")
	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long seq;

	@Column(name = "ORGAN_NAME", length = 20, nullable = false)
	private String organName;

	@Column(name = "STATUS", length = 1, nullable = false)
	private StatusType status;

	@OneToMany(mappedBy = "organEntity", cascade = CascadeType.REMOVE)
	private List<UserEntity> userEntity;

	@Builder
	public OrganEntity(
			@Size(max = 20) String organName, @Size(max = 1) StatusType status) {

		this.organName = organName;
		this.status = status;
	}
}
