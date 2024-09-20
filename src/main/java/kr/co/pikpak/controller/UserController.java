package kr.co.pikpak.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.pikpak.service.UserService;

@RestController
public class UserController {
	@Autowired
	private UserService us;
	
	@GetMapping("/admin/users/search/{search}")
	public String userListFilter() {
		
		return null;
	}
	
	@PostMapping("/admin/check/id")
	public String checkUserId(@RequestParam(value="user_id", required = false) String userId) {
		String responseText = "";
		try {
			int userCount = us.ctnFromView(userId);
			if (userCount == 0) {
				responseText = "Y";
			}
			else {
				responseText = "N";
			}
		} catch (Exception e) {
			responseText = "N";
		}
		return responseText;
	}
}
