package com.example.ac.demo;

import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class DemoService {
	private Demo find() {
		// test data in DB
		return new Demo("A", "B", "C");
	}

	@PreAuthorize("@permissions.isReadable(principal.roleAuthUser, #fields)")
	public Demo getDemo(@P("fields") Set<String> fields) {
		Demo demo = find();
		if (!fields.contains("a")) {
			demo.setA(null);
		}
		if (!fields.contains("b")) {
			demo.setB(null);
		}
		if (!fields.contains("c")) {
			demo.setC(null);
		}
		return demo;
	}

	@PreAuthorize("@permissions.isWritable(principal.roleAuthUser, #demo)")
	public Demo setDemo(@P("demo") Demo d) {
		Demo demo = find();
		if (d.getA() != null) {
			demo.setA(d.getA());
		}
		if (d.getB() != null) {
			demo.setB(d.getB());
		}
		if (d.getC() != null) {
			demo.setC(d.getC());
		}
		return demo;
	}
}
