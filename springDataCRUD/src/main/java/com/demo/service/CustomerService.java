package com.demo.service;

import java.util.List;

import com.demo.dto.CardDTO;
import com.demo.dto.CustomerDTO;
import com.demo.dto.LoanDTO;
import com.demo.exception.CustomerException;

public interface CustomerService {

	public Integer addCustomer(CustomerDTO customerDTO) throws CustomerException;
	
	public String updateCustomer(CustomerDTO customerDTO,String emailId, String password) throws CustomerException;
	
	public CustomerDTO getMyDetails(String emailId, String password) throws CustomerException;
	
	public Integer deleteMyAccount(String emailId, String password) throws CustomerException;
	
	public Integer applyForLoan(String emailId,String password, Double amount, String loanType) throws CustomerException;
	
	public List<LoanDTO> myLoans(String emailId, String password) throws CustomerException;
	
	public Integer addCard(String emailId, String password, CardDTO cardDTO) throws CustomerException;
	
	public List<CardDTO> myCards(String emailId, String password) throws CustomerException;
}
