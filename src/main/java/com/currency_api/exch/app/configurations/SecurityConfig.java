package com.currency_api.exch.app.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;

import com.currency_api.exch.app.security.AuthConverter;
import com.currency_api.exch.app.security.AuthManager;


@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

	/*Esto me sirve para Spring starter web, con webflux hay conflicto*/
	/*@Bean
	/*SecurityFilterChain securityFilterChain(HttpSecurity  http) throws Exception {
        http
            .authorizeRequests(authorize -> authorize
                .anyRequest().permitAll() 
            )
            .csrf(csrf -> csrf.disable())
            .headers(headers -> headers.frameOptions().disable());
        
        return http.build();
    }*/
    
	/* Para webflux */
	@Bean
	SecurityWebFilterChain securityFilterChain(ServerHttpSecurity  http, AuthManager authManager, AuthConverter authConverter) throws Exception {
        
		AuthenticationWebFilter authWebFilter = new AuthenticationWebFilter(authManager);
		authWebFilter.setServerAuthenticationConverter(authConverter);
		
		http
            .authorizeExchange(authorize -> 
            	//authorize.anyExchange().permitAll()
            	authorize.pathMatchers("/login", "/api/users").permitAll()
            	.anyExchange().authenticated()
            )
            .addFilterAt(authWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
            .csrf(csrf -> csrf.disable())
            .headers(headers -> headers.frameOptions().disable());
            //.addFilterAt(jwtFilter, SecurityWebFiltersOrder.AUTHENTICATION);
        
        return http.build();
    }
	
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
}
