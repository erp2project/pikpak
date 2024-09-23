package kr.co.pikpak.security;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler{
	@Override
	public void handle(HttpServletRequest req, HttpServletResponse res,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		 if (auth != null) {
			 res.sendRedirect("/auth/access-error");
		 }
		 else {
			 res.sendRedirect("/auth/login-error");	// 이거는 다른곳에서 핸들링 해야함
		 }
	}
}
