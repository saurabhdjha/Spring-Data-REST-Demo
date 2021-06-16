package com.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.dto.CustomerDTO;
import com.demo.entity.Customer;
import com.demo.exception.CustomerException;
import com.demo.repository.CustomerRepository;



@Service(value="CustomerService")
@Transactional
public class CustomerServiceImpl implements CustomerService{

	@Autowired
	private CustomerRepository customerRepository;
	
	@Override
	public Integer addCustomer(CustomerDTO customerDTO) throws CustomerException{
		
		Customer customerdb=customerRepository.findByEmailId(customerDTO.getEmailId());
		if(customerdb!=null)
			throw new CustomerException("Service.CUSTOMER_ALREADY_EXIST");
		
		if(customerDTO.getEmailId()==null || customerDTO.getPassword()==null || customerDTO.getName()==null || customerDTO.getBirthDate()==null)
			throw new CustomerException("Service.CANNOT_BE_NULL");
		Customer customer=new Customer();
		customer.setName(customerDTO.getName());
		customer.setBirthDate(customerDTO.getBirthDate());
		customer.setCity(customerDTO.getCity());
		customer.setEmailId(customerDTO.getEmailId());
		customer.setPhoneNo(customerDTO.getPhoneNo());
		customer.setPassword(customerDTO.getPassword());
		
		Customer customerSaved=customerRepository.save(customer);

		return customerSaved.getCustomerId();
	}

	@Override
	public String updateCustomer(CustomerDTO customerDTO, String emailId, String password) throws CustomerException{
		
		Customer customerdb=customerRepository.findByEmailId(emailId);
		if(customerdb==null)
			throw new CustomerException("Service.CUSTOMER_DOESNT_EXIST");

		if(!password.equalsIgnoreCase(customerdb.getPassword()))
			throw new CustomerException("Service.INCORRECT_PASSWORD");
		
		String message="";
		// can change only phoneNumber, city and password
		String newPhoneNumber=customerDTO.getPhoneNo();
		if(newPhoneNumber!=null)
		{
			customerdb.setPhoneNo(newPhoneNumber);
			message+="phoneNumber ";
		}
	
		String newCity=customerDTO.getCity();
		if(newCity!=null)
		{
			customerdb.setCity(newCity);
			message+="city ";
		}
		
		String newPassword=customerDTO.getPassword();
		if(newPassword!=null)
		{
			customerdb.setPassword(newPassword);
			message+="password ";
		}
		
		if(message==null || message.isEmpty())
			return "nothing updated!!";
		
		return message+" updated succesfully!!";
	}

	@Override
	public CustomerDTO getMyDetails(String emailId, String password) throws CustomerException{

		Customer customer=customerRepository.findByEmailId(emailId);
		
		if(customer==null)
			throw new CustomerException("Service.CUSTOMER_DOESNT_EXIST");
		if(!customer.getPassword().equalsIgnoreCase(password))
			throw new CustomerException("Service.INCORRECT_PASSWORD");
		
		CustomerDTO customerDTO=new CustomerDTO();
		
		customerDTO.setCustomerId(customer.getCustomerId());
		customerDTO.setName(customer.getName());
		customerDTO.setBirthDate(customer.getBirthDate());
		customerDTO.setCity(customer.getCity());
		customerDTO.setEmailId(customer.getEmailId());
		customerDTO.setPhoneNo(customer.getPhoneNo());
		customerDTO.setPassword(customer.getPassword());
		
		return customerDTO;
	}

	@Override
	public Integer deleteMyAccount(String emailId, String password) throws CustomerException{

		Customer customer=customerRepository.findByEmailId(emailId);
		
		if(customer==null)
			throw new CustomerException("Service.CUSTOMER_DOESNT_EXIST");
		if(!customer.getPassword().equalsIgnoreCase(password))
			throw new CustomerException("Service.INCORRECT_PASSWORD");
		
		int customerId=customer.getCustomerId();
		customerRepository.delete(customer);
		return customerId;
	}


}
