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
	private LoginService LoginService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain)
			throws ServletException, IOException {
		String authorizationHeader = req.getHeader("Authorization");
		//final String authorizationHeader = req.getHeader("Cookie");
		
		if (authorizationHeader == null) {
			String accessToken = CookieUtility.getCookie(req, "accessToken");
			if (accessToken != null) {
				authorizationHeader = "Bearer " + accessToken;
				res.setHeader("Authorization", authorizationHeader);
			}
		}
		
		//System.out.println(req.getRequestURI());
		//System.out.println("Headertest : " + authorizationHeader);
		
		
		String username = null;
		String jwt = null;
		
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) { //request에 authorization header있는경우
			jwt = authorizationHeader.substring(7);
			
			try {
				username = JWTUtil.extractUsername(jwt);
				System.out.println("good");
            } catch (IllegalArgumentException e) {
            	System.out.println("Error occurred while retrieving Username from Token");
            } catch (ExpiredJwtException e) {
            	System.out.println("The token has expired");
            } catch (SignatureException e) {
            	System.out.println("Authentication Failed. Invalid username or password.");
            }
		}
		else {	//request에 authorization 해더 없는 경우
			//System.out.println("Bearer string not found, ignoring the header");
		}
		
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = LoginService.loadUserByUsername(username);
			
			if (JWTUtil.isTokenValid(jwt, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
						new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				
				System.out.println("test" + SecurityContextHolder.getContext());
            }
		}
		filterChain.doFilter(req,res);
	}
}
