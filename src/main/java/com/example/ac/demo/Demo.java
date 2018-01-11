package com.example.ac.demo;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Demo {
	private String a;
	private String b;
	private String c;

	public Demo() {
	}

	public Demo(String a, String b, String c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}

	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}

	public String getB() {
		return b;
	}

	public void setB(String b) {
		this.b = b;
	}

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}

	@Override
	public String toString() {
		return "Demo{" + "a='" + a + '\'' + ", b='" + b + '\'' + ", c='" + c + '\'' + '}';
	}
}
