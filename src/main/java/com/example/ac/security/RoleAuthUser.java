package com.example.ac.security;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class RoleAuthUser {
	private final String clientId;
	private final Set<String> role;

	public RoleAuthUser(String clientId, Collection<String> role) {
		this.clientId = clientId;
		this.role = Collections.unmodifiableSet(new LinkedHashSet<>(role));
	}

	public String getClientId() {
		return clientId;
	}

	public Set<String> getRole() {
		return role;
	}

	public boolean hasRole(String role) {
		return this.role.contains(role);
	}

	@Override
	public String toString() {
		return "RoleAuthUser{" + "clientId='" + clientId + '\'' + ", role=" + role + '}';
	}
}
