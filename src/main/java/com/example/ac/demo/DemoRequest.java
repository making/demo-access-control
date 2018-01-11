package com.example.ac.demo;

import javax.validation.constraints.NotNull;

public class DemoRequest {
	@NotNull
	private Demo fields;

	public Demo getFields() {
		return fields;
	}

	public void setFields(Demo fields) {
		this.fields = fields;
	}

}
