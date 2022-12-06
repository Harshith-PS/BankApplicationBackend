package com.app.service;

import java.util.List;

import com.app.dto.AddressDTO;
import com.app.dto.CustomerDTO;
import com.app.exception.BankException;

public interface CustomerService {

	public CustomerDTO getCustomer(Integer customerId) throws BankException;
	
	public Integer addCustomer(CustomerDTO customerDTO) throws BankException;
	
	public void updateAddress(Integer customerId, AddressDTO addressDTO) throws BankException;
	
	public Integer deleteCustomer (Integer customerId) throws BankException;
	
	public void deleteCustomerOnly(Integer customerId) throws BankException;
	
	public List<CustomerDTO> getAllCustomers() throws BankException;
		
	
	
}
