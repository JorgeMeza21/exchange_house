package com.currency_api.exch.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.currency_api.exch.app.services.CustomUserDetailsService;

import reactor.core.publisher.Mono;

@Component
public class AuthManager implements ReactiveAuthenticationManager{

	@Autowired
	private JwtTokenProvider jwtProvider;
	
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Override
	public Mono<Authentication> authenticate(Authentication authentication) {
		return Mono.justOrEmpty(authentication)
				.cast(BearerToken.class)
				.flatMap(t -> {
					String userName = jwtProvider.getUsernameFromToken(t.getCredentials());
					Mono<UserDetails> userFounded = userDetailsService.findByUsername(userName);
										
					return userFounded.map(u -> {
						if (jwtProvider.validateToken(u, t.getCredentials()))
							return new UsernamePasswordAuthenticationToken(u.getUsername(), u.getPassword(), u.getAuthorities());
						
						return new UsernamePasswordAuthenticationToken(u.getUsername(), u.getPassword(), u.getAuthorities());
						//return Mono.error(new IllegalArgumentException("Invalid token"));
					});
				});
	}
	
	

	

}
