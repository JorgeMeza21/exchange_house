package com.currency_api.exch.app.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.currency_api.exch.app.models.ExchangeType;
import com.currency_api.exch.app.models.Role;
import com.currency_api.exch.app.models.Transaction;
import com.currency_api.exch.app.models.User;
import com.currency_api.exch.app.repository.ExchangeTypeRepository;
import com.currency_api.exch.app.repository.RoleRepository;
import com.currency_api.exch.app.repository.TransactionRepository;
import com.currency_api.exch.app.repository.UserRepository;


@Service
public class UserServiceImpl implements IUserService{

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private TransactionRepository transacRepo;
	
	@Autowired
	private ExchangeTypeRepository exchTypeRepo;
	
	@Autowired
	private PasswordEncoder encoder;

	@Override
	@Transactional(readOnly = true)
	public Iterable<User> findAll() {
		return repository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<User> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	@Transactional
	public User save(User user) {
		user.setIsEnable(true);
		
		Optional<Role> roleUser = roleRepo.findByName("ROLE_USER");
		List<Role> roles = new ArrayList<>();
				
		roleUser.ifPresent(roles::add);
		
		if (user.isAdmin()) {
			Optional<Role> roleAdmin = roleRepo.findByName("ROLE_ADMIN");
			roleAdmin.ifPresent(roles::add);
		}
		
		user.setRoles(roles);
		user.setPassword(encoder.encode(user.getPassword()));
		
		return repository.save(user);	
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> findByName(String name) {
		return repository.findByName(name);
	}

	@Override
	@Transactional
	public Transaction convertAmount(String isoCurrOrigin, String isoCurrFinal, Double amountToChange) {
		
		ExchangeType exchangeRate = exchTypeRepo.getExchangeRate(isoCurrOrigin, isoCurrFinal);
		
		if(exchangeRate == null)
			return null;
		
		Double finalAmount = exchangeRate.getConversion() * amountToChange;
		Transaction t = new Transaction(amountToChange, finalAmount, new Date(), exchangeRate);
		
		return transacRepo.save(t);
	}
	
	
}

