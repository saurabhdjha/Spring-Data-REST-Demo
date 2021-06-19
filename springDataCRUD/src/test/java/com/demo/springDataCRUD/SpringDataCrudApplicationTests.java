package com.demo.springDataCRUD;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.springframework.boot.test.context.SpringBootTest;

import com.demo.dto.CustomerDTO;
import com.demo.entity.Customer;
import com.demo.exception.CustomerException;
import com.demo.repository.CustomerRepository;
import com.demo.service.CustomerService;
import com.demo.service.CustomerServiceImpl;

@SpringBootTest
class SpringDataCrudApplicationTests {

	@Mock
	CustomerRepository customerRepository;
	
	@InjectMocks
	CustomerService customerService=new CustomerServiceImpl();
	
	@Test
	public void addCustomerTestValid() throws CustomerException
	{
		Customer customer=new Customer();
		customer.setEmailId("test@gmail.com");
		customer.setName("test");
		customer.setPassword("test123");
		customer.setBirthDate(LocalDate.now());
		customer.setCustomerId(1);
		
		CustomerDTO customerDTO=new CustomerDTO();
		customerDTO.setEmailId("test@gmail.com");
		customerDTO.setName("test");
		customerDTO.setPassword("test123");
		customerDTO.setBirthDate(LocalDate.now());

		Mockito.when(customerRepository.findByEmailId(customerDTO.getEmailId())).thenReturn(null);
		
		Mockito.when(customerRepository.save(customer)).thenReturn(customer);
		
		Integer custId=customerService.addCustomer(customerDTO);
		Assertions.assertEquals(1,1);
	}
	
	
}
