package com.springboot.blog.demospringbootblogrestapi.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CatagoryDto {

	
	private long id;
	private String nameString;
	private String descriptionString;
}
