package com.spring.rest;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.spring.model.Student;
import com.spring.repo.StudentRepository;

@Controller
public class StudentController {

	@Autowired
	private StudentRepository sRepo;

	@GetMapping
	public Principal retrievePrincipal(Principal principal) {
		return principal;
	}

	@GetMapping("/")
	public String onLoadForm(Model model) {

		Student st = new Student();

		model.addAttribute("form", st);

		return "register";
	}

	@PostMapping("/register")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String addStudent(@ModelAttribute Student st, Model mo) {

		Student save = sRepo.save(st);

		if (save != null) {

			mo.addAttribute("msg", "Added");

		}

		else {

			mo.addAttribute("msg", "NotAdded");
		}

		return "redirect:/";
	}
}
