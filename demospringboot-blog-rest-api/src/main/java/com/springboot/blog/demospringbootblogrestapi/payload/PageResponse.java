package com.springboot.blog.demospringbootblogrestapi.payload;

import java.util.List;

import lombok.Data;

@Data
public class PageResponse {
	private List<PostDto> content;
	private int pageNo;
	private int pageSize;
	private int totalElements;
	private int totalPages;
	private boolean last;
}
