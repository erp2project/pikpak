package kr.co.pikpak.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.pikpak.device.CookieUtility;
import kr.co.pikpak.security.CustomUserDetailsService;
import kr.co.pikpak.security.JWTUtility;



@ControllerAdvice
public class GlobalControllerAdvice {
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@ModelAttribute("activeUserName")
	public String getNameFromContext() {
		String result = "";
		result = userDetailsService.userNameFromContext();	
		
		/*
		String accessToken = CookieUtility.getCookie(req, "accessToken");
		System.out.println(accessToken);
		*/
		
		
		//JWT Expired 상황에는 500. 에러만 뜸 --> try security context holder but i need to add username...only id left (NOT TRUE)
		//System.out.println(JWTUtil.isTokenExpired(accessToken));
		/*
		if(JWTUtil.isTokenExpired(accessToken)!=true) {
			Map<String, String> activeUserData = JWTUtil.extractUserData(accessToken);
			result = activeUserData.get("uname");
		}
		*/
		return result;
	};
	
	@ModelAttribute("activeUserType")
	public String getTypeFromContext() {
		String result = "";
		result = userDetailsService.userTypeFromContext();
		return result;
	}
	
	
}
