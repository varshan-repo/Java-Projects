package com.springboot.blog.demospringbootblogrestapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.blog.demospringbootblogrestapi.Entity.Catagory;

@Repository
public interface CatagoryRepository extends JpaRepository<Catagory, Long>{

	
}
