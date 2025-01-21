package com.currency_api.exch.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.currency_api.exch.app.models.ExchangeType;

public interface ExchangeTypeRepository extends CrudRepository<ExchangeType, Long>{

	public List<ExchangeType> findBySourceCurrency(String sourceCurrency);
	
	@Query("Select e from ExchangeType e where e.sourceCurrency = ?1 and e.finalCurrency = ?2")
	public Optional<ExchangeType> getExchangeRate(String isoCurrOrigin, String isoCurrFinal);
}
