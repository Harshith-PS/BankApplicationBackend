package com.app.api;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.app.BankApplication;
import com.app.dto.AddressDTO;
import com.app.dto.CustomerDTO;
import com.app.exception.BankException;
import com.app.service.CustomerService;

@RestController
@RequestMapping(value = "/bankapp")
public class CustomerAPI {

	@Autowired
	public CustomerService customerService;
	
	public static final Log LOGGER = LogFactory.getLog(BankApplication.class); 
	
	@Autowired
	public Environment environment;
	
	@GetMapping(value = "/customer")
	public ResponseEntity<List<CustomerDTO>> findAllCustomers() throws BankException {
		List<CustomerDTO> listOfCustomer = new ArrayList<>();
		try {
		 listOfCustomer = customerService.getAllCustomers();	
		}
		
		catch(Exception e) {
			if(e.getMessage() != null) {
				LOGGER.error(environment.getProperty(e.getMessage(), "Something went wrong"));
			}
		}
		return new ResponseEntity<List<CustomerDTO>>(listOfCustomer, HttpStatus.OK) ;		
	}
	
	@GetMapping(value ="/customer/{customerId}")
	public ResponseEntity<CustomerDTO> findCustomer(@PathVariable (value="customerId") Integer customerId) throws BankException {
		CustomerDTO customerDTO = new CustomerDTO();
		try {
		 customerDTO = customerService.getCustomer(customerId);
		}
		catch(Exception e) {
			if(e.getMessage() != null) {
				LOGGER.error(environment.getProperty(e.getMessage(), "Something went wrong"));				
			}
		}		
		return new ResponseEntity<CustomerDTO>(customerDTO,HttpStatus.OK);		
	}
	
	@PostMapping(value = "/customer")
	public ResponseEntity<String> addCustomer(@RequestBody CustomerDTO customerDTO) throws BankException {
		String successMessage="";
		try {
			Integer customerId = customerService.addCustomer(customerDTO);
			 successMessage = environment.getProperty("API.INSERT_SUCCESS") + " with customer id: " + customerId;
			
		} catch (Exception e) {
			if(e.getMessage()!=null) {
				LOGGER.error(environment.getProperty(e.getMessage(), "Something went wrong"));
			}
		}
		return new ResponseEntity<String>(successMessage,HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/customer/{customerId}")
	public ResponseEntity<String> updateAddress(@PathVariable Integer customerId, @RequestBody CustomerDTO customerDTO) throws BankException {
		String resposeMessage ="";
		try {
			customerService.updateAddress(customerId, customerDTO.getAddress());
			resposeMessage = environment.getProperty("API.UPDATE_SUCCESS") + "with customer id:" + customerId;
		} catch (Exception e) {
			if(e.getMessage()!=null) {
				LOGGER.error(environment.getProperty(e.getMessage(), "Something went wrong"));
			}
		}
		return new ResponseEntity<String>(resposeMessage, HttpStatus.OK);
	}
	
	@DeleteMapping(value="/customer/{customerId}")
	public ResponseEntity<String> deleteCustomer(@PathVariable Integer customerId) throws BankException {
		String responseMessage = "";
		try {
			customerService.deleteCustomer(customerId);
			responseMessage = environment.getProperty("API.DELETED_SUCCESS") + "  " + customerId;
		} catch (Exception e) {
			LOGGER.error(environment.getProperty(e.getMessage(), "Something went wrong"));
		}
		return new ResponseEntity<String>(responseMessage, HttpStatus.OK);
	}
	
	@GetMapping(value="/getrestcustomer/{customerId}")
	public ResponseEntity<CustomerDTO> getRestAddress(@PathVariable Integer customerId) throws BankException {
		
		String url = "http://localhost:1234/bankapp/customer/{customerId}";
		RestTemplate restTemplate = new RestTemplate();
		CustomerDTO customer = restTemplate.getForObject(url, CustomerDTO.class, customerId);
		return new ResponseEntity<CustomerDTO>(customer, HttpStatus.OK);	
		
	}
	
	
	@PostMapping(value="/addnewrestcustomer")
	public ResponseEntity<String> addNewCustomer(@RequestBody CustomerDTO customerDTO) throws BankException {
		String url ="http://localhost:1234/bankapp/customer";
		RestTemplate restTemplate = new RestTemplate();
		String response = restTemplate.postForObject(url, customerDTO, String.class);
		return new ResponseEntity<String>(response, HttpStatus.CREATED);
	}
}
