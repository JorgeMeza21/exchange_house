package com.currency_api.exch.app.controllers;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.currency_api.exch.app.models.ExchangeType;
import com.currency_api.exch.app.services.IExchgTypeService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ExchgTypeHandler {
	
	@Autowired
	private IExchgTypeService service;

	public Mono<ServerResponse> findAll(ServerRequest request) {
		//return ServerResponse.ok().body(service.findAll(), ExchangeType.class);
		//return ServerResponse.ok().body(BodyInserters.fromObject(service.findAll()));
		return ServerResponse.ok().body(Flux.fromIterable(service.findAll()), ExchangeType.class);

	}
	
	public Mono<ServerResponse> save(ServerRequest request) {	
		Mono<ExchangeType> objToSave = request.bodyToMono(ExchangeType.class);
		
		return objToSave.flatMap(et -> {				
				ExchangeType e = service.save(et);
				
				return ServerResponse.created(URI.create("/api/exchgtypes/" + e.getId()))
						.body(Mono.just(e), ExchangeType.class);
		});
	}
	
	public Mono<ServerResponse> update(ServerRequest request) {
		Long id = Long.valueOf(request.pathVariable("id"));
		Mono<ExchangeType> objToUpdate = request.bodyToMono(ExchangeType.class);
		Optional<ExchangeType> objFinded = service.findById(id);
		
		return Mono.just(objFinded).flatMap(optObj -> {
					if (optObj.isEmpty()) {
						return ServerResponse.notFound().build();
					}
					
					return objToUpdate.flatMap(obj -> ServerResponse.created(URI.create("/api/exchgtypes/" + obj.getId()))
							.body(Mono.just(service.save(obj)), ExchangeType.class));
										 
				}
				);
		
	}
	
	public Mono<ServerResponse> findById(ServerRequest request){
		Long id = Long.valueOf(request.pathVariable("id"));
		Optional<ExchangeType> objFinded = service.findById(id);

		return Mono.just(objFinded).flatMap(optObj -> {
			if (optObj.isEmpty()) {
				return ServerResponse.notFound().build();
			}
			
			return ServerResponse.ok()
					.body(Mono.just(optObj.get()), ExchangeType.class); 
		}
		);
	}
	
	public Mono<ServerResponse> findBySourceCurrency(ServerRequest request){
		String isoCode = request.pathVariable("iso_code");
		Iterable<ExchangeType> dataFinded = service.findBySourceCurrency(isoCode);

		return ServerResponse.ok().body(Flux.fromIterable(dataFinded), ExchangeType.class);
	}
}
