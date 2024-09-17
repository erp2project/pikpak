//package com.counseling.cms.jwt;
package reference;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
	@Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expirationTime;

    //JWT 토큰을 생성 후 return
    public String generateToken(String userId, String authority) {
    	Claims claims=Jwts.claims();
    	claims.put("userId", userId);
    	claims.put("authority", authority);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
    

	//refresh토큰 생성
	public String generateRefreshToken(String userId, String authority) {
		Claims claims = Jwts.claims();
		claims.put("userId", userId);
		claims.put("authority", authority);	
		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+expirationTime))
				.signWith(SignatureAlgorithm.HS512, secretKey)
				.compact();
	}
    
    //JWT 토큰에서 Claims 정보를 추출
    public Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    //JWT 토큰에서 사용자 아이디 추출
    public String extractUserId(String token) {
        return extractClaims(token).get("userId").toString();
    }
    
    //JWT 토큰에서 사용자 아이디 추출
    public String extractAuthority(String token) {
        return extractClaims(token).get("authority").toString();
    }

    //토큰 만료 여부 체크
    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }
    

}
