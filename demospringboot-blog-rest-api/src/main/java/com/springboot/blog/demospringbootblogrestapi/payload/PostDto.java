package com.springboot.blog.demospringbootblogrestapi.payload;

import java.util.Set;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostDto {
	
	private int id;
	
	//Should not be empty or null
	//should have atleast 2 character
	
	@NotEmpty
	@Size(min = 2, message = "Title should have atleast 2 character")
	private String title;
	
	// should not be null or empty
	// should have 10 characters atleast
	
	@NotEmpty
	@Size(min = 10, message = "Description should have atleast 10 character")
	private String description;
	
	// should not be null or empty
	@NotEmpty
	private String content;
	
	private Set<CommentDto> comments;
	
	private Long catagoryId;
}
