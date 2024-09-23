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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.co.pikpak.dto.LoginDTO;
import kr.co.pikpak.security.CustomUserDetails;
import kr.co.pikpak.security.CustomUserDetailsService;
import kr.co.pikpak.security.JWTUtility;
import kr.co.pikpak.service.LoginService;

@RestController
public class LoginController {
	@Resource(name="LoginDTO")
	LoginDTO ldto;
	
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Autowired
	private HttpSession session;
	
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired 
    private JWTUtility JWTUtil;
	
	PrintWriter pw = null;
	
	// 회원 로그인 및 토큰 생성
	@PostMapping("/login/auth")
	public String createAuthenticationToken(LoginDTO logindto, HttpServletResponse res, HttpSession sess) throws Exception {
		// Fetch API 결과 핸들링
		String responseText = "";
		
		try {
			// 회원 정보 확인
			Authentication authenticate = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(logindto.getUser_id(), logindto.getUser_pw())
			);
			//SecurityContextHolder.getContext().setAuthentication(authenticate);
			
			// 회원 DB 접속
			final CustomUserDetails userDetails = userDetailsService.loadUserByUsername(logindto.getUser_id());
			
			// 생성할 JWT 토큰
			String token = null;
			
			// 회원이 운영자 일경우 운영자 권한 레벨 까지 저장
			if (userDetails.getUserAuthority().equals("operator")) {
				String operatorLv = userDetailsService.operatorLvByUserId(logindto.getUser_id());
				token = JWTUtil.generateOperatorToken(userDetails, operatorLv);
			}
			else {
				token = JWTUtil.generateToken(userDetails);
			}
			
			// 클라이언트 사이드 토큰 저장 (Request 해더 미사용시 대신 사용)
			Cookie cookie = new Cookie("accessToken", token);
			cookie.setMaxAge(60*60*24+7);
			cookie.setHttpOnly(true);
			cookie.setPath("/");
			res.addCookie(cookie);
			
			// 세션에 아이디 정보 저장
			sess.setAttribute("activeUserID", logindto.getUser_id());
			sess.setMaxInactiveInterval(60*60*24+7);
			
			
			responseText = "ok";
		} catch (BadCredentialsException e) {
			responseText = "회원정보가 일치하지 않습니다";
		} catch (Exception e) {
			responseText = "서버 문제로 로그인이 실패하였습니다. 관리자에게 문의하세요";
		}
		
		return responseText;
	}
	
	
	
	
}
