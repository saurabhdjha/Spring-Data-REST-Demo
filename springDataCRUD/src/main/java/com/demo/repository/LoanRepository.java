package com.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.entity.Loan;

@Repository("LoanRepository")
public interface LoanRepository extends CrudRepository<Loan,Integer>{

	//Query creation using @Query Annotataion
	@Query("SELECT p FROM Loan p where p.customer.customerId = :customerId")
	List<Loan> getAllLoansOfCustomer(@Param("customerId") Integer customerId);
}
