package com.example.ac.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class RoleAuthUserDetails implements UserDetails {
	private final RoleAuthUser roleAuthUser;

	public RoleAuthUserDetails(RoleAuthUser roleAuthUser) {
		this.roleAuthUser = roleAuthUser;
	}

	public RoleAuthUser getRoleAuthUser() {
		return roleAuthUser;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		String[] roles = this.roleAuthUser.getRole().stream()
				.map(r -> "ROLE_" + r.toUpperCase()).toArray(String[]::new);
		return AuthorityUtils.createAuthorityList(roles);
	}

	@Override
	public String getPassword() {
		return "N/A";
	}

	@Override
	public String getUsername() {
		return this.roleAuthUser.getClientId();
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
