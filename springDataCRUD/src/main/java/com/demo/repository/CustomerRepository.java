package com.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.entity.Customer;

@Repository(value="CustomerRepository")
public interface CustomerRepository extends CrudRepository<Customer,Integer>{

	// Query Creation based on MethodName
	Customer findByEmailId(String emailId);
	
	//Query creation using @Query Annotation
	@Query("SELECT c FROM Customer c WHERE c.city = :city")
	List<Customer> getAllCustomersByCity(@Param("city") String city);
}
