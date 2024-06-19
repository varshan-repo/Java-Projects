package com.springboot.blog.demospringbootblogrestapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.demospringbootblogrestapi.payload.CatagoryDto;
import com.springboot.blog.demospringbootblogrestapi.serviceimpl.CatagoryServiceImpl;

import jakarta.persistence.Id;

@RestController
public class CatagoryController {

	private CatagoryServiceImpl catagoryServiceImpl;
	
	/*
	 * we can also auto-wire service class to avoid tight coupling
	 * i,e private CatagoryService catagoryService;
	*/
	public CatagoryController(CatagoryServiceImpl catagoryServiceImpl) {
		this.catagoryServiceImpl = catagoryServiceImpl;
	}

	@PostMapping("/api/catagory/add")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> addCatagory(@RequestBody CatagoryDto catagoryDto) {
	
		catagoryServiceImpl.addCatagory(catagoryDto);
		return new ResponseEntity<String>("catagory added succesfully!!!", HttpStatus.CREATED);
	}
	
	@GetMapping("/api/catagory/{id}")
	public ResponseEntity<CatagoryDto> getCatagory(@PathVariable long id){
		
		CatagoryDto result = catagoryServiceImpl.getCatagory(id);
		return new ResponseEntity<CatagoryDto>(result, HttpStatus.FOUND);
	}
	
	@GetMapping("/api/catagory")
	public ResponseEntity<List<CatagoryDto>> getAllCatagory(){
		List<CatagoryDto> result = catagoryServiceImpl.getAllCatagory();
		return new ResponseEntity<List<CatagoryDto>>(result, HttpStatus.FOUND);
	}
	
	@PutMapping("/api/catagory/update/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CatagoryDto> updateCatagory(@PathVariable long id , @RequestBody CatagoryDto catagoryDto){
		CatagoryDto resultCatagoryDto = catagoryServiceImpl.updateCatagoryDto(id, catagoryDto);
		return new ResponseEntity<CatagoryDto>(resultCatagoryDto, HttpStatus.CREATED);
		
	}
	
	@DeleteMapping("/api/catagory/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deletecatagory(@PathVariable long id) {

		String resultString = catagoryServiceImpl.deleteCatagoryDto(id);
		return new ResponseEntity<String>(resultString, HttpStatus.OK);
	}
	
}
