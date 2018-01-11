package com.example.ac.demo;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
public class DemoController {
	private final DemoService demoService;

	public DemoController(DemoService demoService) {
		this.demoService = demoService;
	}

	@GetMapping("demo")
	public Demo getDemo(@RequestParam("fields") Set<String> fields) {
		return this.demoService.getDemo(fields);
	}

	@PostMapping("demo")
	public Demo setDemo(@Validated @RequestBody DemoRequest request) {
		return this.demoService.setDemo(request.getFields());
	}

}
