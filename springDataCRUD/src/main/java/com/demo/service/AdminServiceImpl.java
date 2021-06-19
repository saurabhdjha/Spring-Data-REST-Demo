package com.demo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.dto.AddressDTO;
import com.demo.dto.AdminDTO;
import com.demo.dto.CustomerDTO;
import com.demo.dto.LoanDTO;
import com.demo.entity.Admin;
import com.demo.entity.Customer;
import com.demo.entity.Loan;
import com.demo.exception.CustomerException;
import com.demo.repository.AdminRepository;
import com.demo.repository.CustomerRepository;
import com.demo.repository.LoanRepository;

@Service(value="AdminService")
public class AdminServiceImpl implements AdminService{

	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private LoanRepository loanRepository;
	
	
	//validate  admin login
	@Override
	public boolean validateAdminLogin(AdminDTO adminDTO) throws CustomerException{
		
		Admin admindb=adminRepository.findByLoginId(adminDTO.getLoginId());
		if(admindb==null)
			throw new CustomerException("Service.ADMIN_NOT_AVAILABLE");
		if(!admindb.getPassword().equalsIgnoreCase(adminDTO.getPassword()))
			throw new CustomerException("Service.INCORRECT_PASSWORD_ADMIN");
		
	    return true;
		
	}

	// Get a customer details
	@Override
	public CustomerDTO getCustomerById(AdminDTO adminDTO, Integer customerId) throws CustomerException {
		if(validateAdminLogin(adminDTO))
		{
			Optional<Customer> customerOpt=customerRepository.findById(customerId);
			if(customerOpt.isEmpty())
				throw new CustomerException("Service.CUSTOMER_DOESNT_EXIST_ADMIN");
			Customer customer=customerOpt.get();
			
			CustomerDTO customerDTO=new CustomerDTO();
			customerDTO.setCustomerId(customer.getCustomerId());
			customerDTO.setName(customer.getName());
			customerDTO.setPassword("**********");
			customerDTO.setBirthDate(customer.getBirthDate());
			customerDTO.setEmailId(customer.getEmailId());
			customerDTO.setPhoneNo(customer.getPhoneNo());
			AddressDTO addressDTO=new AddressDTO();
			addressDTO.setAddress_id(customer.getAddress().getAddress_id());
			addressDTO.setCity(customer.getAddress().getCity());
			addressDTO.setState(customer.getAddress().getState());
			addressDTO.setPincode(customer.getAddress().getPincode());
			customerDTO.setAddress(addressDTO);
			return customerDTO;
		}
		return null;
	}

	// get all customers.
	@Override
	public List<CustomerDTO> getAllCustomers(){
		Iterable<Customer> iterableCust=customerRepository.findAll();
		List<Customer> customers=new ArrayList<>();
		for(Customer customer: iterableCust)
		{
			customers.add(customer);
		}
		
		List<CustomerDTO> customerDTOs=new ArrayList<>();
		for(int i=0;i<customers.size();i++)
		{
			Customer customer=customers.get(i);
			CustomerDTO customerDTO=new CustomerDTO();
			customerDTO.setCustomerId(customer.getCustomerId());
			customerDTO.setName(customer.getName());
			customerDTO.setPassword("**********");
			customerDTO.setBirthDate(customer.getBirthDate());
			customerDTO.setEmailId(customer.getEmailId());
			customerDTO.setPhoneNo(customer.getPhoneNo());
			AddressDTO addressDTO=new AddressDTO();
			addressDTO.setAddress_id(customer.getAddress().getAddress_id());
			addressDTO.setCity(customer.getAddress().getCity());
			addressDTO.setState(customer.getAddress().getState());
			addressDTO.setPincode(customer.getAddress().getPincode());
			customerDTO.setAddress(addressDTO);
			customerDTOs.add(customerDTO);
		}
		
		return customerDTOs;
	}
	
	
   //Get Customer based on City
	@Override
	public List<CustomerDTO> getAllCustomersByCity(String city) {
		
		Iterable<Customer> iterableCust=customerRepository.getAllCustomersByCity(city);
		List<Customer> customers=new ArrayList<>();
		for(Customer customer: iterableCust)
		{
			customers.add(customer);
		}
		
		List<CustomerDTO> customerDTOs=new ArrayList<>();
		for(int i=0;i<customers.size();i++)
		{
			Customer customer=customers.get(i);
			CustomerDTO customerDTO=new CustomerDTO();
			customerDTO.setCustomerId(customer.getCustomerId());
			customerDTO.setName(customer.getName());
			customerDTO.setPassword("**********");
			customerDTO.setBirthDate(customer.getBirthDate());
			customerDTO.setEmailId(customer.getEmailId());
			customerDTO.setPhoneNo(customer.getPhoneNo());
			AddressDTO addressDTO=new AddressDTO();
			addressDTO.setAddress_id(customer.getAddress().getAddress_id());
			addressDTO.setCity(customer.getAddress().getCity());
			addressDTO.setState(customer.getAddress().getState());
			addressDTO.setPincode(customer.getAddress().getPincode());
			customerDTO.setAddress(addressDTO);
			customerDTOs.add(customerDTO);
		}
		
		return customerDTOs;
	}

	//Get All Loan Details of a customer
	@Override
	public List<LoanDTO> getLoanByCustomerId(Integer customerId) {
        
		List<Loan> loans=loanRepository.getAllLoansOfCustomer(customerId);
		
		List<LoanDTO> loanDTOs=new ArrayList<>();
		
		for(Loan loan:loans)
		{
			LoanDTO loanDTO=new LoanDTO();
			loanDTO.setAmount(loan.getAmount());
			loanDTO.setLoanId(loan.getLoanId());
			loanDTO.setLoanType(loan.getLoanType());
			
			if(loan.getStatus().equalsIgnoreCase("issued"))
			{
				loanDTO.setStatus(loan.getStatus());
				loanDTO.setIssueDate(loan.getIssueDate());
			}
			else
			{
				loanDTO.setStatus(loan.getStatus());
			}
			
			if(loan.getCustomer()!=null)
			{
				Customer customer=loan.getCustomer();
				CustomerDTO customerDTO=new CustomerDTO();
				customerDTO.setCustomerId(customer.getCustomerId());
				customerDTO.setName(customer.getName());
				customerDTO.setPassword("**********");
				customerDTO.setBirthDate(customer.getBirthDate());
				customerDTO.setEmailId(customer.getEmailId());
				customerDTO.setPhoneNo(customer.getPhoneNo());
				AddressDTO addressDTO=new AddressDTO();
				addressDTO.setAddress_id(customer.getAddress().getAddress_id());
				addressDTO.setCity(customer.getAddress().getCity());
				addressDTO.setState(customer.getAddress().getState());
				addressDTO.setPincode(customer.getAddress().getPincode());
				customerDTO.setAddress(addressDTO);
				loanDTO.setCustomer(customerDTO);
			}
			loanDTOs.add(loanDTO);
		}
		
		return loanDTOs;
	}

	@Override
	public Integer approveLoan(Integer customerId, Integer loanId) throws CustomerException{
		
		Optional<Customer> customerOpt=customerRepository.findById(customerId);
		if(customerOpt.isEmpty())
			throw new CustomerException("Service.CUSTOMER_DOESNT_EXIST_ADMIN");
		Optional<Loan> loanOpt=loanRepository.findById(loanId);
		if(loanOpt.isEmpty())
			throw new CustomerException("Service.INCORRECT_LOAN_ID");
		
		Loan loan=loanOpt.get();
		Customer customer=customerOpt.get();
		
		if(loan.getCustomer().getCustomerId()!=customer.getCustomerId())
			throw new CustomerException("Service.INCORRECT_LOAN_ID");
		
		loan.setIssueDate(LocalDate.now());
		loan.setStatus("issued");
		Loan loanret=loanRepository.save(loan);
		return loanret.getLoanId();
	}

	@Override
	public Integer rejectLoan(Integer customerId, Integer loanId) throws CustomerException {
		Optional<Customer> customerOpt=customerRepository.findById(customerId);
		if(customerOpt.isEmpty())
			throw new CustomerException("Service.CUSTOMER_DOESNT_EXIST_ADMIN");
		Optional<Loan> loanOpt=loanRepository.findById(loanId);
		if(loanOpt.isEmpty())
			throw new CustomerException("Service.INCORRECT_LOAN_ID");
		
		Loan loan=loanOpt.get();
		Customer customer=customerOpt.get();
		
		if(loan.getCustomer().getCustomerId()!=customer.getCustomerId())
			throw new CustomerException("Service.INCORRECT_LOAN_ID");
		
		if(loan.getStatus().equalsIgnoreCase("issued"))
			throw new CustomerException("Service.CANNOT_REJECT");
		
		loan.setStatus("rejected");
		Loan loanret=loanRepository.save(loan);
		return loanret.getLoanId();
	}
	
	

}
