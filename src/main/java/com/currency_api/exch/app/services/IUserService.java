package com.currency_api.exch.app.services;

import java.util.List;
import java.util.Optional;

import com.currency_api.exch.app.models.Transaction;
import com.currency_api.exch.app.models.User;

public interface IUserService {

	public Iterable<User> findAll();
	public Optional<User> findById(Long Id);
	public User save(User user);
	public List<User> findByName(String name);
	public Transaction convertAmount(String isoCurrOrigin, String isoCurrFinal, Double amountToChange);
	
}
