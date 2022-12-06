package com.app.repository;

import org.springframework.data.repository.CrudRepository;

import com.app.entity.Address;

public interface AddressRepository extends CrudRepository<Address, Integer> {

}
