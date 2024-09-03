package kr.co.pikpak;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class test_controller {
	@GetMapping("/test.do")
	public String test(){
		return null;
	}
}
