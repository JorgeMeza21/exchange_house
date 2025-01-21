package com.currency_api.exch.app.security;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class AuthConverter implements ServerAuthenticationConverter{

	@Override
	public Mono<Authentication> convert(ServerWebExchange exchange) {
		
		return Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
				.filter(h -> h.startsWith("Bearer "))
				.map(h -> {
					String finalToken = h.substring(7);
					exchange.getAttributes().put("token", finalToken);
					return finalToken;
				})
				.map(h -> new BearerToken(h));
	}
	
}
