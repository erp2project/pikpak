package kr.co.pikpak.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DeliveryController {
	
	//납품 등록
	@GetMapping("/delivery/deliveryenroll")
	public String deliveryenroll() {
		
		return null;
	}
}
