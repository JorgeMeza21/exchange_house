package com.currency_api.exch.app.controllers;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.currency_api.exch.app.models.Transaction;
import com.currency_api.exch.app.security.JwtTokenProvider;
import com.currency_api.exch.app.services.ITransactionService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class TransactionHandler {

	@Autowired
	private ITransactionService service;
	
	public Mono<ServerResponse> findAll(ServerRequest request) {
		return ServerResponse.ok().body(Flux.fromIterable(service.findAll()), Transaction.class);
	}
	
	public Mono<ServerResponse> save(ServerRequest request) {	
		Mono<Transaction> objToSave = request.bodyToMono(Transaction.class);
		
		return objToSave.flatMap(t -> {				
				Transaction e = service.save(t);
				
				return ServerResponse.created(URI.create("/api/transactions/" + e.getId()))
						.body(Mono.just(e), Transaction.class);
		});
	}
	
	public Mono<ServerResponse> findById(ServerRequest request) {
		Long id = Long.valueOf(request.pathVariable("id"));
		
		Optional<Transaction> objFinded = service.findById(id);

		return Mono.just(objFinded).flatMap(optObj -> {
			if (optObj.isEmpty()) {
				return ServerResponse.notFound().build();
			}
			
			return ServerResponse.ok()
					.body(Mono.just(optObj.get()), Transaction.class); 
		});
	
	}
	
	public Mono<ServerResponse> findByUser(ServerRequest request) {
		Long userId = Long.valueOf(request.pathVariable("user_id"));

		return ServerResponse.ok().body(Flux.fromIterable(service.findByUser(userId)), Transaction.class);
	}
	
}
