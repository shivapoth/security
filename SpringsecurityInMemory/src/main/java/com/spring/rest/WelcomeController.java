package com.spring.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

	@GetMapping("/")
	public String defaultPage() {

		return "default";
	}

	@GetMapping("/h")
	public String homePage() {

		return "home";
	}

	@GetMapping("/w")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String welcomePage() {

		return "welcome ";
	}

	@GetMapping("/a")
	public String adminPage() {

		return "admin";
	}

	@GetMapping("/e")
	public String empPage() {

		return "employee";
	}

	@GetMapping("/s")
	public String studentPage() {

		return " student";
	}
}
