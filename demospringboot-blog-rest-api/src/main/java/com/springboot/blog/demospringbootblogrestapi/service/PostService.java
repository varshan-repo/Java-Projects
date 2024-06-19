package com.springboot.blog.demospringbootblogrestapi.service;

import java.util.List;

import com.springboot.blog.demospringbootblogrestapi.Entity.Post;
import com.springboot.blog.demospringbootblogrestapi.payload.PageResponse;
import com.springboot.blog.demospringbootblogrestapi.payload.PostDto;

public interface PostService {
	PostDto createPost(PostDto postDto);
	
	Post createPostUsingPost(Post post);
	
	List<PostDto> getAllPost();
	
	PostDto getPostById(int id);
	
	PostDto updatePost(PostDto postDto, int id);
	
	String deletePost(int id);
	
	List<PostDto> getAllPostWithPaging(int pageNo, int pageSize);
	
	List<Post> createPostAddAll(List<Post> post);
	
	PageResponse getAllPostWithCustomPaging(int pageNo, int pageSize);
	
	PageResponse getAllPostWithPagingAndSorting(int pageNo, int pageSize, String sortBy);
	
	PageResponse getAllPostWithSorting(int pageNo, int pageSize, String sortBy, String sortDir);
	
	List<PostDto> getPostByCatagory(long catagoryId);
	
}
