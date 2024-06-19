package com.springboot.blog.demospringbootblogrestapi.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class CommentDto {
	
	
	private int id;
	
	@NotEmpty(message = "Name should not be empty or null")
	private String name;

	@NotEmpty(message = "email should not be empty or null")
	@Email
	private String email;
	
	@NotEmpty(message = "Body should not be empty or null")
	@Size(min = 10, message = "should be atleast 10 character")
	private String body;
}
