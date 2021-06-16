package com.demo.service;

import com.demo.dto.CustomerDTO;
import com.demo.exception.CustomerException;

public interface CustomerService {

	public Integer addCustomer(CustomerDTO customerDTO) throws CustomerException;
	
	public String updateCustomer(CustomerDTO customerDTO,String emailId, String password) throws CustomerException;
	
	public CustomerDTO getMyDetails(String emailId, String password) throws CustomerException;
	
	public Integer deleteMyAccount(String emailId, String password) throws CustomerException;
}
