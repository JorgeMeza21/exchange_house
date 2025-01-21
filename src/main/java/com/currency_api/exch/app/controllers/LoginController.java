package com.currency_api.exch.app.controllers;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.currency_api.exch.app.models.User;
import com.currency_api.exch.app.security.JwtTokenProvider;

import reactor.core.publisher.Mono;

@RestController
public class LoginController {

	@Autowired
	private JwtTokenProvider jwtProv;
	
	@Autowired
	private ReactiveUserDetailsService service;
	
	@Autowired
	private PasswordEncoder encoder;
	
    @PostMapping("/login")
    public Mono<ResponseEntity<Map<Object, String>>> login(@RequestBody User user) {
    	
    	Mono<UserDetails> userFounded = service.findByUsername(user.getUserName());
		Map<Object, String> response = new HashMap<>();

    	return userFounded.flatMap(userD -> {
    		if (encoder.matches(user.getPassword(), userD.getPassword())) {
    			
    			response.put("message", "Login Succesful!");
    			response.put("token", jwtProv.generateToken(user.getUserName()));
    			
    			return Mono.just(ResponseEntity
    					.status(HttpStatus.ACCEPTED)
    					.body(response));
    		}

			response.put("message", "Invalid credentials, try again.");

    		return Mono.just(ResponseEntity
    				.status(HttpStatus.UNAUTHORIZED)
    				.body(response));
    	//}).defaultIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(response)));
		}).defaultIfEmpty(ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(Map.of("message", "Username not founded, register and try again please")));

    }
}
