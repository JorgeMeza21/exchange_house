package com.currency_api.exch.app.services;

import java.util.Optional;

import com.currency_api.exch.app.models.Transaction;

public interface ITransactionService {

	public Transaction save(Transaction transac);
	public Iterable<Transaction> findAll();
	public Iterable<Transaction> findByUser(Long userId);
	public Optional<Transaction> findById(Long id);
}
