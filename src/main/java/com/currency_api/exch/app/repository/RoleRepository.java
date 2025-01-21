package com.currency_api.exch.app.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.currency_api.exch.app.models.Role;


public interface RoleRepository extends CrudRepository<Role, Long>{

	public Optional<Role> findByName(String name);
}
