package com.demo.service;

import java.util.List;

import com.demo.dto.AdminDTO;
import com.demo.dto.CustomerDTO;
import com.demo.dto.LoanDTO;
import com.demo.exception.CustomerException;

public interface AdminService {

	public boolean validateAdminLogin(AdminDTO adminDTO) throws CustomerException;
	
	public CustomerDTO getCustomerById(AdminDTO adminDTO, Integer customerId) throws CustomerException;
	
	public List<CustomerDTO> getAllCustomers();
	
	public List<CustomerDTO> getAllCustomersByCity(String city);
	
	public List<LoanDTO> getLoanByCustomerId(Integer customerId);

	public Integer approveLoan(Integer customerId, Integer loanId) throws CustomerException;
	
	public Integer rejectLoan(Integer customerId, Integer loanId) throws CustomerException;
}
