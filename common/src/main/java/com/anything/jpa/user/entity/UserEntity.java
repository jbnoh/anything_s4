package com.anything.jpa.user.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;

import com.anything.jpa.BaseEntity;
import com.anything.jpa.organ.entity.OrganEntity;
import com.anything.jpa.user.entity.embeddable.UserAuthEm;
import com.anything.type.impl.UserType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(
	name = "A_USER",
	uniqueConstraints = {
		@UniqueConstraint(columnNames = {"USER_ID"})
	}
)
public class UserEntity extends BaseEntity {

	@Column(name = "SEQ")
	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long seq;

	@ManyToOne
	@JoinColumn(name = "ORGAN_SEQ")
	private OrganEntity organEntity;

	@Column(name = "USER_ID", length = 20, nullable = false)
	private String userId;

	@Column(name = "USER_PW", nullable = false)
	private String userPw;

	@Column(name = "USER_NAME", nullable = false)
	private String userName;

	@Column(name = "USER_TYPE", length = 1, nullable = false)
	private UserType userType;

	@ElementCollection
	@CollectionTable(
			name = "A_USER_AUTH",
			joinColumns = @JoinColumn(name = "USER_SEQ")
	)
	private Set<UserAuthEm> userAuthEm = new HashSet<>();

	public void updateUserPw(String userPw) {
		if (StringUtils.isNotBlank(userPw)) {
			this.userPw = userPw;
		}
	}

	public void updateUserName(String userName) {
		if (StringUtils.isNotBlank(userName)) {
			this.userName = userName;
		}
	}

	public void updateUserType(UserType userType) {
		if (userType != null) {
			this.userType = userType;
		}
	}

	public void updateOrganEntity(OrganEntity organEntity) {
		if (organEntity != null) {
			this.organEntity = organEntity;
		}
	}

	@Builder
	public UserEntity(
			OrganEntity organEntity, @Size(max = 20) String userId, String userPw,
			String userName, @Size(max = 1) UserType userType) {

		this.organEntity = organEntity;
		this.userId = userId;
		this.userPw = userPw;
		this.userName = userName;
		this.userType = userType;
	}
}
