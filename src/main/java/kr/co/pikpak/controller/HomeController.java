package kr.co.pikpak.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {
	@PostMapping("/login/verify")
	public String loginVerify() {
		
		return null;
	}
}
