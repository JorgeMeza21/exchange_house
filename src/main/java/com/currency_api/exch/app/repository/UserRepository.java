package com.currency_api.exch.app.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.currency_api.exch.app.models.User;

public interface UserRepository extends CrudRepository<User, Long>{
	
	public List<User> findByName(String name);
	public Optional<User> findByUserName(String userName);
}
