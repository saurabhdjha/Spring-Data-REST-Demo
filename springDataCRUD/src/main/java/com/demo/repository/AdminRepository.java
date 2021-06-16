package com.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.demo.entity.Admin;

public interface AdminRepository extends CrudRepository<Admin,Integer>{

	// Query Creation based on MethodName
	Admin findByLoginId(String loginId);
}
