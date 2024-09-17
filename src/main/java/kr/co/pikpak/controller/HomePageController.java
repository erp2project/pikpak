package kr.co.pikpak.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePageController {
	@GetMapping("/template")
	public String mainPage() {
		return "/shared/template";
	}
	
	@GetMapping("/og")
	public String ogPage() {
		return "/reference/index";
	}
	
	
}
