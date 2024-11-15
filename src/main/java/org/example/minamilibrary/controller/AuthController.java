package org.example.minamilibrary.controller;

import org.example.minamilibrary.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {
	private final UserService userService;
	
	public AuthController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}
}
