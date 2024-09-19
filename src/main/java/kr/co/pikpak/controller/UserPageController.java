package kr.co.pikpak.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.co.pikpak.dto.LoginDTO;
import kr.co.pikpak.service.UserService;

@Controller
public class UserPageController {
	@Autowired
	private UserService us;
	
	@GetMapping("/admin/users")
	public String userListPage(Model m) {
		List<LoginDTO> userList = us.userListFromView();
		m.addAttribute("userList",userList);
		m.addAttribute("userListSize",userList.size());
		return "/user/users";
	}
	
	@GetMapping("/test")
	public String testPage() {
		return "/reference/index";
	}
}
