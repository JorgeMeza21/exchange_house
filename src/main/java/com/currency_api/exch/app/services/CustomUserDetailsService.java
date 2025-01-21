package com.currency_api.exch.app.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.currency_api.exch.app.models.User;
import com.currency_api.exch.app.repository.UserRepository;

import reactor.core.publisher.Mono;

@Service
@Primary
public class CustomUserDetailsService implements ReactiveUserDetailsService {
	
	@Autowired
	private UserRepository repository;

	@Override
	public Mono<UserDetails> findByUsername(String username) {
		Optional<User> optUser = repository.findByUserName(username);
		
		if (optUser.isEmpty()){
			//throw new UsernameNotFoundException(String.format("El usuario %s no se encuentra, desde mi service", username));
			return Mono.empty();	
		}
		
		User user = optUser.get();
		
		List<GrantedAuthority> authorities = user.getRoles()
				.stream()
				.map(role -> new SimpleGrantedAuthority(role.getName()))
				.collect(Collectors.toList());
			
		return Mono.just(new org.springframework.security.core.userdetails.User(
				user.getUserName(),
				user.getPassword(),
				user.isEnable(),
				true,
				true,
				true,
				authorities
				));
	}
	
	

	/*
	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> optUser = repository.findByUserName(username);
		
		if (optUser.isEmpty())
			throw new UsernameNotFoundException(String.format("El usuario %s no se encuentra", username));
		
		User user = optUser.get();
		
		List<GrantedAuthority> authorities = user.getRoles()
				.stream()
				.map(role -> new SimpleGrantedAuthority(role.getName()))
				.collect(Collectors.toList());
			
		return new org.springframework.security.core.userdetails.User(
				user.getUserName(),
				user.getPassword(),
				user.isEnable(),
				true,
				true,
				true,
				authorities
				);
	}
	*/
}
