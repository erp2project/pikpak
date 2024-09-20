package kr.co.pikpak.controller;

import java.io.PrintWriter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.co.pikpak.dto.LoginDTO;
import kr.co.pikpak.security.JWTUtility;
import kr.co.pikpak.service.LoginService;

@RestController
//@RequestMapping("/auth")
public class LoginController {
	@Resource(name="LoginDTO")
	LoginDTO ldto;
	
	@Autowired
	private LoginService LoginService;
	
	@Autowired
	private HttpSession session;
	
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired 
    private JWTUtility JWTUtil;
	
	PrintWriter pw = null;
	
	@PostMapping("/login/auth")
	public ResponseEntity<?> createAuthenticationToken(@ModelAttribute LoginDTO logindto, HttpServletResponse res) throws Exception {
		try {
			Authentication authenticate = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(logindto.getUser_id(), logindto.getUser_pw())
			);
			SecurityContextHolder.getContext().setAuthentication(authenticate);
			System.out.println("ctrl test : " + SecurityContextHolder.getContext());
		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password!!!", e);
		}
		
		final UserDetails userDetails = LoginService.loadUserByUsername(logindto.getUser_id());
		final String jwt = JWTUtil.generateToken(userDetails);
		
		//System.out.println(jwt);
		
		Cookie cookie = new Cookie("accessToken", jwt);
		cookie.setMaxAge(60*60*24+7);
		cookie.setHttpOnly(true);
		cookie.setPath("/");
		
		res.addCookie(cookie);
		
		return ResponseEntity.ok(jwt);
	}
	
	
	
	
}
