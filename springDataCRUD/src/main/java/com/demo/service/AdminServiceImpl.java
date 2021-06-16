package com.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.dto.AdminDTO;
import com.demo.dto.CustomerDTO;
import com.demo.entity.Admin;
import com.demo.entity.Customer;
import com.demo.exception.CustomerException;
import com.demo.repository.AdminRepository;
import com.demo.repository.CustomerRepository;

@Service(value="AdminService")
public class AdminServiceImpl implements AdminService{

	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Override
	public boolean validateAdminLogin(AdminDTO adminDTO) throws CustomerException{
		
		Admin admindb=adminRepository.findByLoginId(adminDTO.getLoginId());
		if(admindb==null)
			throw new CustomerException("Service.ADMIN_NOT_AVAILABLE");
		if(!admindb.getPassword().equalsIgnoreCase(adminDTO.getPassword()))
			throw new CustomerException("Service.INCORRECT_PASSWORD");
		
	    return true;
		
	}

	@Override
	public CustomerDTO getCustomerById(AdminDTO adminDTO, Integer customerId) throws CustomerException {
		if(validateAdminLogin(adminDTO))
		{
			Optional<Customer> customerOpt=customerRepository.findById(customerId);
			if(customerOpt.isEmpty())
				throw new CustomerException("Service.CUSTOMER_DOESNT_EXIST");
			Customer customer=customerOpt.get();
			
			CustomerDTO customerDTO=new CustomerDTO();
			customerDTO.setCustomerId(customer.getCustomerId());
			customerDTO.setName(customer.getName());
			customerDTO.setPassword("**********");
			customerDTO.setBirthDate(customer.getBirthDate());
			customerDTO.setCity(customer.getCity());
			customerDTO.setEmailId(customer.getEmailId());
			customerDTO.setPhoneNo(customer.getPhoneNo());
			
			return customerDTO;
		}
		return null;
	}

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
			customerDTO.setCity(customer.getCity());
			customerDTO.setEmailId(customer.getEmailId());
			customerDTO.setPhoneNo(customer.getPhoneNo());
			
			customerDTOs.add(customerDTO);
		}
		
		return customerDTOs;
	}

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
			customerDTO.setCity(customer.getCity());
			customerDTO.setEmailId(customer.getEmailId());
			customerDTO.setPhoneNo(customer.getPhoneNo());
			
			customerDTOs.add(customerDTO);
		}
		
		return customerDTOs;
	}
	
	

}
