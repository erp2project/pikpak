package kr.co.pikpak.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.pikpak.device.CookieUtility;
import kr.co.pikpak.service.LoginService;

@Component
public class JWTRequestFilter extends OncePerRequestFilter{
	@Autowired
	private JWTUtility JWTUtil;
	
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain)
			throws ServletException, IOException {
		String authHeader = req.getHeader("Authorization");
		//final String authorizationHeader = req.getHeader("Cookie");
		
		if (authHeader == null) {
			String accessToken = CookieUtility.getCookie(req, "accessToken");
			if (accessToken != null) {
				authHeader = "Bearer " + accessToken;
				res.setHeader("Authorization", authHeader);
			}
		}
		
		//System.out.println(req.getRequestURI());
		//System.out.println("Headertest : " + authorizationHeader);
		
		
		String requestURI = req.getRequestURI();
		
		if (requestURI.startsWith("/resources/") || requestURI.startsWith("/login")) {
			filterChain.doFilter(req, res);
			return;
		}
		

		String userId = null;
		String token = null;
		
		// Authorization 헤더가 존재하고 Bearer로 시작할 때
		if (authHeader != null && authHeader.startsWith("Bearer ")) { //request에 authorization header있는경우
			token = authHeader.substring(7);
			
			try {
				userId = JWTUtil.extractUserId(token);
				//System.out.println("good");
				System.out.println(userId);
            } catch (IllegalArgumentException e) {
            	System.out.println("Error occurred while retrieving Username from Token");
            } catch (ExpiredJwtException e) {
            	//System.out.println("The token has expired");
            } catch (SignatureException e) {
            	//System.out.println("Authentication Failed. Invalid username or password.");
            }
			
            // 유저 이름이 존재하고 현재 인증 정보가 없는 경우
            if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            	CustomUserDetails userDetails = userDetailsService.loadUserByUsername(userId);
                // 토큰이 만료되었을 때
                if (JWTUtil.isTokenExpired(token)) {
                	res.sendRedirect("/admin/login");
                    return;
                }
                // 유효한 토큰일 경우
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                
                
                if (requestURI.equals("/login")) {
                	res.sendRedirect("/admin/apply-list");
                    return;
                }
            }			
			
            //System.out.println("ttestetst : " + SecurityContextHolder.getContext().getAuthentication());
			
		}
		else {	//request에 authorization 해더 없는 경우
			System.out.println(requestURI);
			System.out.println("Bearer string not found, ignoring the header");
		}

		filterChain.doFilter(req,res);
	}
}
