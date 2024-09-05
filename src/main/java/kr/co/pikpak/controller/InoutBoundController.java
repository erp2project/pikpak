package kr.co.pikpak.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InoutBoundController {
	
	@GetMapping("inoutbound/inboundreq")
	public String inboundreq() {
		return null;
	}
	
	@GetMapping("/inoutbound/comp_search_popup")
	public String showCompSearchPopup() {
	    return "inoutbound/comp_search_popup";  // Thymeleaf 템플릿 경로
	}
}
