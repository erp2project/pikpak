package kr.co.pikpak.security;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JWTUtility {
	//Message : secret
	//Secret-key: 123123
	private String secretKey = "f826e963aea8e14cec02b74f8cc67bb9830adaed1191c0f29a41c38bc558e217";
	
	private int jwtExpiration = 3600000;	//1 HR

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
	
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> subject = new HashMap<>();
        Map<String, Object> claims = new HashMap<>();
        
        //System.out.println(userDetails.getUsername());
        //System.out.println(userDetails.getAuthorities());
        
        subject.put("alg", "HS256");
        subject.put("typ", "JWT");
        claims.put("uid", userDetails.getUsername());
        claims.put("utype", userDetails.getAuthorities());
        
        return createToken(subject, claims);
    }
    
    private String createToken(Map<String, Object> subject, Map<String, Object> claims) {
        return Jwts.builder()
        		.setHeader(subject)
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSignInKey(),SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }
    
	public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

	private Claims extractAllClaims(String token) {
		return Jwts
				.parserBuilder()
				.setSigningKey(getSignInKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    
    private Date extractExpiration(String token) {
    	return extractClaim(token, Claims::getExpiration);
    }


	
}
