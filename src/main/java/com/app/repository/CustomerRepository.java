package com.app.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.app.entity.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Integer>  {

	public Optional<Customer> findByEmailId(String emailId) ;
	
}
