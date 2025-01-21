package com.currency_api.exch.app.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.currency_api.exch.app.models.Transaction;
import com.currency_api.exch.app.repository.TransactionRepository;

@Service
public class TransactionServiceImpl implements ITransactionService{

	@Autowired
	private TransactionRepository repository;
	
	@Override
	public Transaction save(Transaction transac) {
		return repository.save(transac);
	}

	@Override
	public Iterable<Transaction> findAll() {
		return repository.findAll();
	}

	@Override
	public Iterable<Transaction> findByUser(Long userId) {
		return repository.findByUser(userId);
	}

	@Override
	public Optional<Transaction> findById(Long id) {
		return repository.findById(id);
	}

}
