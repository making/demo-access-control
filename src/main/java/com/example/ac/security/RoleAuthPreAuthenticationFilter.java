package com.example.ac.security;

import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class RoleAuthPreAuthenticationFilter
		extends AbstractPreAuthenticatedProcessingFilter {

	@Override
	protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
		String roleAuth = request.getHeader("X-Role-Auth");
		MultiValueMap<String, String> variables = WebUtils.parseMatrixVariables(roleAuth);
		if (variables.containsKey("client_id") && variables.containsKey("role")) {
			String clientId = variables.getFirst("client_id");
			List<String> role = variables.get("role");
			return new RoleAuthUser(clientId, role);
		}
		return null;
	}

	@Override
	protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
		return "N/A";
	}
}