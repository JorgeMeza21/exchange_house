package com.currency_api.exch.app.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.currency_api.exch.app.controllers.ExchgTypeHandler;
import com.currency_api.exch.app.controllers.TransactionHandler;
import com.currency_api.exch.app.controllers.UserHandler;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class RouterFunctionsConfig {

	@Bean
	public RouterFunction<ServerResponse> routes(ExchgTypeHandler handler, UserHandler userHandler, TransactionHandler transacHandler) {
		return route(GET("/api/exchgtypes"), handler::findAll)
				.andRoute(POST("/api/exchgtypes"), handler::save)
				.andRoute(GET("/api/exchgtypes/{id}"), handler::findById)
				.andRoute(GET("/api/exchgtypes/by_source/{iso_code}"), handler::findBySourceCurrency)
				.andRoute(PUT("/api/exchgtypes/{id}"), handler::update)
				.andRoute(GET("/api/users"), userHandler::findAll)
				.andRoute(GET("/api/users/by_name/{name}"), userHandler::findByName)
				.andRoute(GET("/api/users/{id}"), userHandler::findById)
				.andRoute(POST("/api/users"), userHandler::save)
				.andRoute(POST("/api/users/exchangeMoney/{isoSourceCurr}/{isoFinalCurr}/{amount}"), userHandler::convertAmount)
				.andRoute(GET("/api/transactions"), transacHandler::findAll)
				.andRoute(GET("/api/transactions/{id}"), transacHandler::findById)
				.andRoute(GET("/api/transactions/by_user/{user_id}"), transacHandler::findByUser);

	}
	
}
