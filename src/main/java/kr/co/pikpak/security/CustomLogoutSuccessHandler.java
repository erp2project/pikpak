package kr.co.pikpak.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.pikpak.device.CookieUtility;
import kr.co.pikpak.dto.LoginAccessDTO;
import kr.co.pikpak.repo.LoginAccessRepo;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler{
	
	@Autowired
	private LoginAccessRepo lar;
	
	@Autowired
	private JWTUtility JWTUtil;
	
	@Override
	public void onLogoutSuccess(HttpServletRequest req, HttpServletResponse res, Authentication auth)
			throws IOException, ServletException {
		// Request 헤더에 Authorization 있는 경우
		String authHeader = req.getHeader("Authorization");
		
		// Request 헤더에 Authorization 없는 경우 
		if (authHeader == null) {
			String accessToken = CookieUtility.getCookie(req, "accessToken");
			if (accessToken != null) {
				authHeader = "Bearer " + accessToken;
				res.setHeader("Authorization", authHeader);
			}
		}
		
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);
            LoginAccessDTO ldto = new LoginAccessDTO();

            ldto.setUser_id(JWTUtil.extractUserId(jwt)); 
            ldto.setJsession_id(req.getRequestedSessionId());
            lar.updateAccessLog(ldto);
        }
		
		res.sendRedirect("/logout/end");
	}
}
