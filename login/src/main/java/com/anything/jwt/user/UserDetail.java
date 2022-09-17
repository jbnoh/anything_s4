package com.anything.jwt.user;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.anything.jpa.organ.entity.OrganEntity;
import com.anything.type.impl.UserType;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDetail implements UserDetails {

	private static final long serialVersionUID = -5464850746918871214L;

	private String username;

	private String password;

	private OrganEntity organEntity;

	private UserType userType;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
