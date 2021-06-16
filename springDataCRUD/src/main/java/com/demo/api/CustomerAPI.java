package com.demo.api;


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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.dto.CustomerDTO;
import com.demo.exception.CustomerException;
import com.demo.service.CustomerService;

@RestController
@RequestMapping(value="/customer-api")
public class CustomerAPI {

	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private Environment environment;
	
	private static final Log LOGGER=LogFactory.getLog(CustomerAPI.class);
	
	// Customer: To create new Account
	@PostMapping(value="/addCustomer")
	public ResponseEntity<String> addCustomer(@RequestBody CustomerDTO customerDTO)
	{
		try
		{
			Integer id=customerService.addCustomer(customerDTO);
			String message="Congrats!! Your account has been created with id: "+id;
			return new ResponseEntity<String>(message,HttpStatus.OK);
		}
		catch(CustomerException e)
		{
			String message=environment.getProperty(e.getMessage());
			LOGGER.error(message);
			return new ResponseEntity<String>(message,HttpStatus.BAD_REQUEST);
		}
		
	}
	
	// Customer: To update his/her details.
	@PostMapping(value="/updateCustomer/{emailId}/{password}")
	public ResponseEntity<String> updateCustomer(@RequestBody CustomerDTO customerDTO, @PathVariable String emailId, @PathVariable String password)
	{
		try {
			String message=customerService.updateCustomer(customerDTO, emailId, password);
			return new ResponseEntity<String>(message,HttpStatus.OK);
		}
		catch(CustomerException e)
		{
			String message=environment.getProperty(e.getMessage());
			LOGGER.error(message);
			return new ResponseEntity<String>(message,HttpStatus.BAD_REQUEST);
		}
	}
	
	// Customer: can see his/her details on entering email and password
	@GetMapping(value="/getMyDetail/{emailId}/{password}")
	public ResponseEntity<CustomerDTO> getMyDetails(@PathVariable String emailId, @PathVariable String password)
	{
		try {
			CustomerDTO customerDTO=customerService.getMyDetails(emailId, password);
		    return new ResponseEntity<CustomerDTO>(customerDTO,HttpStatus.OK);
		}
		catch(CustomerException e)
		{
			LOGGER.error(environment.getProperty(e.getMessage()));
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);	
		}
		
	}
	
	//Customer: can delete his account by providing valid emailId and Password
	@DeleteMapping(value="/deleteMyAccount/{emailId}/{password}")
	public ResponseEntity<String> deleteMyAccount(@PathVariable String emailId, @PathVariable String password)
	{
		try {
			Integer id=customerService.deleteMyAccount(emailId, password);
			String message="Your Account with id: "+id+" has been deleted successfully!!";
			return new ResponseEntity<String>(message,HttpStatus.OK);
		}
        catch(Exception e)
		{
        	String message=environment.getProperty(e.getMessage());
        	LOGGER.error(message);
			return new ResponseEntity<String>(message,HttpStatus.BAD_REQUEST);
		}
		
	}
}
