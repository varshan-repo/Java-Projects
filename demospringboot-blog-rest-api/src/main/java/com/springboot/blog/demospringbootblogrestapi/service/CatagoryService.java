package com.springboot.blog.demospringbootblogrestapi.service;

import java.util.List;

import com.springboot.blog.demospringbootblogrestapi.payload.CatagoryDto;


public interface CatagoryService {

	CatagoryDto addCatagory(CatagoryDto catagoryDto); 
	
	CatagoryDto getCatagory(long id);
	
	List<CatagoryDto> getAllCatagory();
	
	CatagoryDto updateCatagoryDto(long id, CatagoryDto catagoryDto);
	
	String deleteCatagoryDto(long id);
}
