package com.currency_api.exch.app.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.currency_api.exch.app.models.ExchangeType;
import com.currency_api.exch.app.repository.ExchangeTypeRepository;

@Service
public class ExchgTypeServiceImpl implements IExchgTypeService {

	@Autowired
	private ExchangeTypeRepository repository;

	@Override
	@Transactional(readOnly = true)
	public Iterable<ExchangeType> findAll() {
		return repository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<ExchangeType> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	@Transactional
	public ExchangeType save(ExchangeType excType) {
		return repository.save(excType);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<ExchangeType> findBySourceCurrency(String isoCode) {
		return repository.findBySourceCurrency(isoCode);
	}
	
}
