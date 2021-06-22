package com.demo.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.dto.AdminDTO;
import com.demo.dto.CustomerDTO;
import com.demo.dto.LoanDTO;
import com.demo.exception.CustomerException;
import com.demo.service.AdminService;

@RestController
@RequestMapping(value="/admin-api")
public class AdminAPI {

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private Environment environment;
	
	
	@GetMapping(value="/customerById/{customerId}")
	public ResponseEntity<CustomerDTO> getCustomerById(@RequestBody AdminDTO adminDTO, @PathVariable Integer customerId) throws CustomerException
	{
			CustomerDTO customerDTO=adminService.getCustomerById(adminDTO, customerId);
			return new ResponseEntity<CustomerDTO>(customerDTO,HttpStatus.OK);
	}
	
	@GetMapping(value="/customers")
	public ResponseEntity<List<CustomerDTO>> getAllCustomers() throws CustomerException
	{
	  List<CustomerDTO> customerDTOs=adminService.getAllCustomers();
	  return new ResponseEntity<>(customerDTOs,HttpStatus.OK); 
	}
	
	@GetMapping(value="/customers/{city}")
	public ResponseEntity<List<CustomerDTO>> getAllCustomersByCity(@PathVariable String city) throws CustomerException
	{
		List<CustomerDTO> customerDTOs=adminService.getAllCustomersByCity(city);
		 return new ResponseEntity<>(customerDTOs,HttpStatus.OK); 
	}
	
	@GetMapping(value="/loansByCustomerId/{customerId}")
	public ResponseEntity<List<LoanDTO>> myLoans(@PathVariable Integer customerId) throws CustomerException
	{
			List<LoanDTO> loanDTOs=adminService.getLoanByCustomerId(customerId);
			return new ResponseEntity<List<LoanDTO>>(loanDTOs,HttpStatus.OK);
	}
	
	@PutMapping(value="/approveLoan/{customerId}/{loanId}")
	public ResponseEntity<String> approveLoan(@PathVariable Integer customerId, @PathVariable Integer loanId) throws CustomerException
	{
			Integer loanIdR=adminService.approveLoan(customerId, loanId);
			String message=environment.getProperty("API.LOAN_SUCCESS") + loanIdR;
			return new ResponseEntity<String>(message,HttpStatus.OK);
	}
	
	@PutMapping(value="/rejectLoan/{customerId}/{loanId}")
	public ResponseEntity<String> rejectLoan(@PathVariable Integer customerId, @PathVariable Integer loanId) throws CustomerException
	{
			Integer loanIdR=adminService.rejectLoan(customerId, loanId);
			String message=environment.getProperty("API.LOAN_REJECTED") + loanIdR;
			return new ResponseEntity<String>(message,HttpStatus.OK);
	}
}
