package com.currency_api.exch.app.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.currency_api.exch.app.models.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, Long>{

	@Query("select t from Transaction t join fetch t.user u where u.id = ?1")
	public Iterable<Transaction> findByUser(Long userId);
}
