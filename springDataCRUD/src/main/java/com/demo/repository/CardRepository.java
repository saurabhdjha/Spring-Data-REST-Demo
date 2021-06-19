package com.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.demo.entity.Card;

public interface CardRepository extends CrudRepository<Card,Integer>{

}
