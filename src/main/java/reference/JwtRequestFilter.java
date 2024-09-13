//package com.counseling.cms.jwt;
package reference;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import reference.CookieUtility;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        
        if (authHeader == null) {
            String accessToken = CookieUtility.getCookie(request, "accessToken");
            if (accessToken != null) {
                authHeader = "Bearer " + accessToken;
                response.setHeader("Authorization", authHeader);
            }
        }
        
        String requestURI = request.getRequestURI();
        if (requestURI.startsWith("/css/") || requestURI.startsWith("/js/") || requestURI.startsWith("/images/") || requestURI.equals("/admin/login")) {
            filterChain.doFilter(request, response);
            return;
        }
        // Authorization 헤더가 존재하고 Bearer로 시작할 때
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String username = jwtUtil.extractUserId(token);
            
            // 유저 이름이 존재하고 현재 인증 정보가 없는 경우
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                // 토큰이 만료되었을 때
                if (jwtUtil.isTokenExpired(token)) {
                    response.sendRedirect("/admin/login");
                    return;
                }
                // 유효한 토큰일 경우
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                
                if (request.getRequestURI().equals("/admin/login")) {
                    response.sendRedirect("/admin/apply-list");
                    return;
                }
            }
        }
        
        filterChain.doFilter(request, response);
    }
}
