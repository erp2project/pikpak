package kr.co.pikpak.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class HomePageController {
	
	@GetMapping("/home")
	public String homePage() {
		return "/home/home";
		//return "/notice/noticeTable"; 
	}
	
}
