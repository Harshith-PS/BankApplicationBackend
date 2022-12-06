package com.app;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import com.app.dto.AddressDTO;
import com.app.dto.CustomerDTO;
import com.app.service.CustomerService;
import java.time.LocalDate;


@SpringBootApplication
public class BankApplication //implements CommandLineRunner 
{

	@Autowired
	public CustomerService customerService;
	
	@Autowired
	public Environment environment;
	
	public static final Log LOGGER = LogFactory.getLog(BankApplication.class); 
	
	public static void main(String[] args) {
		SpringApplication.run(BankApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		//getCustomer();
//		//addCustomer();
//		//updateAddress();
//		//deleteCustomer();
//		//deleteCustomerOnly();
//	}

	private void addCustomer() {
		try {
			
			CustomerDTO customer1 = new CustomerDTO();
			//customer1.setCustomerId(1238);
			customer1.setDateOfBirth(LocalDate.of(1999, 2, 27));
			customer1.setEmailId("harshith@gmail.com");
			customer1.setName("Harshith P S");
			
			AddressDTO address1 = new AddressDTO();
			address1.setAddressId((long) 104);
			address1.setCity("Mysore");
			address1.setStreet("Abhishek Circle");
			customer1.setAddress(address1);
			
			Integer customer1Id = customerService.addCustomer(customer1);
			LOGGER.info(  customer1Id + ": "+ environment.getProperty("UserInterface.CUSTOMER_ADDED"));
			
		} catch(Exception e) {
			if(e.getMessage() != null) {
				LOGGER.error(environment.getProperty(e.getMessage(), "Something went wrong"));
			}
		}
		
	}

	public void getCustomer() {
		try {
			CustomerDTO customerDTO = customerService.getCustomer(123400);
			LOGGER.info(customerDTO);
			
		} catch(Exception e) {
			String message = environment.getProperty(e.getMessage(),"Some Exception Happened");
			LOGGER.info(message);			
		}
	}

	
	public void updateAddress()
	{
	try {
		AddressDTO addressDTO = new AddressDTO();
		addressDTO.setCity("Hassan");
		addressDTO.setStreet("Palya");
		
		customerService.updateAddress(1238, addressDTO);
		
		LOGGER.info(environment.getProperty("UserInterface.CUSTOMER_ADDRESS_UPDATED_SUCCESSFULLY"));
	}
	catch (Exception e) {
		String message = environment.getProperty(e.getMessage(),"Some Exception Happened");
		LOGGER.info(message);
	}
	}
	
	public void deleteCustomer() {
		try {
			Integer customerId = customerService.deleteCustomer(1235);
			LOGGER.info(customerId + " " +environment.getProperty("UserInterface.CUSTOMER_DELETED"));
		}
		catch (Exception e) {
			String message = environment.getProperty(e.getMessage(),"Some Exception Happened");
			LOGGER.info(message);
		}
	}
	
	public void deleteCustomerOnly() {
		try {
			customerService.deleteCustomerOnly(1236);
			LOGGER.info(environment.getProperty("UserInterface.CUSTOMER_DELETED_ADDRESS_LEFT"));
		} catch (Exception e) {
			String message = environment.getProperty(e.getMessage(),"Some Exception Happened");
			LOGGER.info(message);
		}
	}
	
}



