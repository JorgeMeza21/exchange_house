package com.currency_api.exch.app.controllers;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.currency_api.exch.app.models.User;
import com.currency_api.exch.app.services.IUserService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class UserHandler {

	@Autowired
	private IUserService service;

	public Mono<ServerResponse> findAll(ServerRequest request) {
		return ServerResponse.ok().body(Flux.fromIterable(service.findAll()), User.class);
	}
	
	public Mono<ServerResponse> save(ServerRequest request) {	
		Mono<User> objToSave = request.bodyToMono(User.class);
		
		return objToSave.flatMap(user -> {				
				User e = service.save(user);
				
				return ServerResponse.created(URI.create("/api/users/" + e.getId()))
						.body(Mono.just(e), User.class);
		});
	}
	
	public Mono<ServerResponse> findById(ServerRequest request) {
		Long id = Long.valueOf(request.pathVariable("id"));
		
		Optional<User> objFinded = service.findById(id);

		return Mono.just(objFinded).flatMap(optObj -> {
			if (optObj.isEmpty()) {
				return ServerResponse.notFound().build();
			}
			
			return ServerResponse.ok()
					.body(Mono.just(optObj.get()), User.class); 
		});
	
	}
	
	public Mono<ServerResponse> findByName(ServerRequest request) {
		String name = request.pathVariable("name");

		return ServerResponse.ok().body(Flux.fromIterable(service.findByName(name)), User.class);
	}
	
}
