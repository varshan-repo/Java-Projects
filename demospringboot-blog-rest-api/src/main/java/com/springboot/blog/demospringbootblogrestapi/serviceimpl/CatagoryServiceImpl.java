package com.springboot.blog.demospringbootblogrestapi.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.springboot.blog.demospringbootblogrestapi.Entity.Catagory;
import com.springboot.blog.demospringbootblogrestapi.exception.ResourceNotFoundException;
import com.springboot.blog.demospringbootblogrestapi.payload.CatagoryDto;
import com.springboot.blog.demospringbootblogrestapi.repository.CatagoryRepository;
import com.springboot.blog.demospringbootblogrestapi.service.CatagoryService;

@Service
public class CatagoryServiceImpl implements CatagoryService {

	
	private CatagoryRepository catagoryRepository;
	private ModelMapper mapper;
	
	public CatagoryServiceImpl(CatagoryRepository catagoryRepository,
						       ModelMapper mapper) {
		this.catagoryRepository = catagoryRepository;
		this.mapper=mapper;
	}

	@Override
	public CatagoryDto addCatagory(CatagoryDto catagoryDto) {

		Catagory catagory = maptoEntity(catagoryDto);
		Catagory savedCatagory = catagoryRepository.save(catagory);
		CatagoryDto resultCatagoryDto = mapToDto(savedCatagory);
		
		return resultCatagoryDto;
	}

	@Override
	public CatagoryDto getCatagory(long id) {		
		
		Catagory catagory = catagoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Catagory", "id", id));
		
		return mapToDto(catagory);
	}
	
	@Override
	public List<CatagoryDto> getAllCatagory() {
		
		List<Catagory> catagoryList = catagoryRepository.findAll();
		
		List<CatagoryDto> catagoryDtosList = catagoryList.stream()
				.map((catagoryDto)-> mapper.map(catagoryDto, CatagoryDto.class))
				.collect(Collectors.toList());
		
		return catagoryDtosList;
	}
	
	@Override
	public CatagoryDto updateCatagoryDto(long id, CatagoryDto catagoryDto) {

		Catagory catagory = catagoryRepository.findById(id)
		.orElseThrow(()-> new ResourceNotFoundException("catagory", "id", id));
		
		//catagory.setId(catagoryDto.getId());
		catagory.setNameString(catagoryDto.getNameString());
		catagory.setDescriptionString(catagoryDto.getDescriptionString());
		
		Catagory updateCatagory = catagoryRepository.save(catagory);
		
		return mapToDto(updateCatagory);
	}
	
	@Override
	public String deleteCatagoryDto(long id) {
		
		Catagory catagory = catagoryRepository.findById(id)
		.orElseThrow(()-> new ResourceNotFoundException("catagory", "id", id));
		
		catagoryRepository.delete(catagory);
		
		return "Deleted";
	}
	
	// convert entity to DTO
	public CatagoryDto mapToDto(Catagory catagory) {
		return mapper.map(catagory, CatagoryDto.class);
	}

	//convert dto to entity
	public Catagory maptoEntity(CatagoryDto catagoryDto) {
		return mapper.map(catagoryDto, Catagory.class);
	}



}
