package com.currency_api.exch.app.security;

import java.security.Key;
import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

	private final Key key;
	private final JwtParser parser;
	
	public JwtTokenProvider() {
		this.key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Genera una clave secreta
		this.parser = Jwts.parserBuilder().setSigningKey(key).build();
	}
	
	
    public String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 3600000); // 1 hora

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key)
                .compact();
    }
    
    public String getUsernameFromToken(String token) {
    		Claims claims = parser.parseClaimsJws(token).getBody();
        	return claims.getSubject();
    }

    public boolean validateToken(UserDetails user, String token) {
    	
    	Claims claims = parser.parseClaimsJws(token).getBody();

    	boolean unexpired = claims.getExpiration().after(new Date());
    	
    	return unexpired && user.getUsername() == claims.getSubject();
    }
    
}
