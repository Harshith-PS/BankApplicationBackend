package com.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dto.AddressDTO;
import com.app.dto.CustomerDTO;
import com.app.entity.Address;
import com.app.entity.Customer;
import com.app.exception.BankException;
import com.app.repository.CustomerRepository;

@Service(value = "customerService")
@Transactional
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Override
	public CustomerDTO getCustomer(Integer customerId) throws BankException {
		
		Optional<Customer> optional = customerRepository.findById(customerId);
		Customer customer = optional.orElseThrow(()-> new BankException("Service.INVALID_CUSTOMERID"));
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setCustomerId(customer.getCustomerId());
		customerDTO.setEmailId(customer.getEmailId());
		customerDTO.setDateOfBirth(customer.getDateOfBirth());
		customerDTO.setName(customer.getName());
		
		AddressDTO addressDTO = new AddressDTO();
		addressDTO.setAddressId(customer.getAddress().getAddressId());
		addressDTO.setCity(customer.getAddress().getCity());
		addressDTO.setStreet(customer.getAddress().getStreet());
		
		customerDTO.setAddress(addressDTO);
		
		return customerDTO;
	}

	@Override
	public Integer addCustomer(CustomerDTO customerDTO) throws BankException {
		Optional<Customer> checkCustomer = customerRepository.findByEmailId(customerDTO.getEmailId());
		if(checkCustomer.isPresent()) {
			throw new BankException("Service.CUSTOMER_EXISTS");
		}
		Customer customer = new Customer();
		customer.setCustomerId(customerDTO.getCustomerId());
		customer.setDateOfBirth(customerDTO.getDateOfBirth());
		customer.setEmailId(customerDTO.getEmailId());
		customer.setName(customerDTO.getName());
		
		Address address = new Address();
				address.setAddressId(customerDTO.getAddress().getAddressId());
				address.setCity(customerDTO.getAddress().getCity());
				address.setStreet(customerDTO.getAddress().getStreet());
		
		customer.setAddress(address);
		
		Customer resCustomer = customerRepository.save(customer);
				
		return resCustomer.getCustomerId();
	}

	@Override
	public void updateAddress(Integer customerId, AddressDTO addressDTO) throws BankException {
		
		Optional<Customer> optional = customerRepository.findById(customerId);
		
		Customer customer = optional.orElseThrow(() -> new BankException("Service.INVALID_CUSTOMERID") );
		
		Address address = customer.getAddress();
				address.setCity(addressDTO.getCity());
				address.setStreet(addressDTO.getStreet());
				
				customer.setAddress(address);
						
		return;
	}

	@Override
	public Integer deleteCustomer(Integer customerId) throws BankException {
		
		Optional<Customer> optional = customerRepository.findById(customerId);
		
		Customer customer = optional.orElseThrow(() -> new BankException("Service.INVALID_CUSTOMERID"));
		
		customerRepository.delete(customer);
		
		return customerId;
	}

	@Override
	public void deleteCustomerOnly(Integer customerId) throws BankException {
		
		Optional<Customer> optional = customerRepository.findById(customerId);
		
		Customer customer = optional.orElseThrow(() -> new BankException("Service.INVALID_CUSTOMERID"));
		
		customer.setAddress(null);
		
		customerRepository.delete(customer);
		
	}

	@Override
	public List<CustomerDTO> getAllCustomers() throws BankException {
		
		List<CustomerDTO> listCustomerDTO = new ArrayList<CustomerDTO>();
		Iterable<Customer> iterable = customerRepository.findAll();
		for (Customer customer : iterable) {
			CustomerDTO customerDTO = new CustomerDTO();
			customerDTO.setCustomerId(customer.getCustomerId());
			customerDTO.setDateOfBirth(customer.getDateOfBirth());
			customerDTO.setEmailId(customer.getEmailId());
			customerDTO.setName(customer.getName());
			
			AddressDTO addressDTO = new AddressDTO();
			addressDTO.setAddressId(customer.getAddress().getAddressId());
			addressDTO.setCity(customer.getAddress().getCity());
			addressDTO.setStreet(customer.getAddress().getStreet());
			
			customerDTO.setAddress(addressDTO);
			listCustomerDTO.add(customerDTO);
		}
		if(listCustomerDTO.isEmpty())
		{
			throw new BankException("Service.CUSTOMERS_NOT_FOUND");
		}
		
		return listCustomerDTO;
	}

}
