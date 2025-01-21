package com.currency_api.exch.app.services;

import java.util.Optional;

import com.currency_api.exch.app.models.ExchangeType;

public interface IExchgTypeService {

	public Iterable<ExchangeType> findAll();
	public Optional<ExchangeType> findById(Long id);
	public Iterable<ExchangeType> findBySourceCurrency(String isoCode);
	public ExchangeType save(ExchangeType excType);
}
