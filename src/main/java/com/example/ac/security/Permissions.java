package com.example.ac.security;

import com.example.ac.demo.Demo;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class Permissions {
	public boolean isReadable(RoleAuthUser roleAuthUser, Set<String> fields) {
		if (!roleAuthUser.hasRole("get")) {
			return false;
		}
		if ("A".equals(roleAuthUser.getClientId())) {
			return !fields.contains("c"); // A is not allowed to read 'c'.
			// throw new AccessDeniedException("foo") if you want to change the error
			// message
		}
		return false;
	}

	public boolean isWritableWritable(RoleAuthUser roleAuthUser, Demo demo) {
		if (!roleAuthUser.hasRole("set")) {
			return false;
		}
		if ("B".equals(roleAuthUser.getClientId())) {
			return demo.getA() == null && demo.getB() == null; // B is not allowed to
																// write 'a' nor 'b'.
		}
		return false;
	}
}
