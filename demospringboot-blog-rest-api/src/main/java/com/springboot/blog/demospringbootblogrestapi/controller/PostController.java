package com.springboot.blog.demospringbootblogrestapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.demospringbootblogrestapi.Entity.Post;
import com.springboot.blog.demospringbootblogrestapi.payload.PageResponse;
import com.springboot.blog.demospringbootblogrestapi.payload.PostDto;
import com.springboot.blog.demospringbootblogrestapi.service.PostService;
import com.springboot.blog.demospringbootblogrestapi.utils.AppConstants;

import jakarta.validation.Valid;

@RestController
public class PostController {
	
	private PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}
	
	// create blog post
	// @Valid - for implementing validation hibernate validator 
	
	@PostMapping("/api/post")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
		return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
		 
	}
	
	@PostMapping("/api/post/createAll")
	@PreAuthorize("hasRole('ADMIN')")
	public List<Post> createAllPost(@Valid @RequestBody List<Post> post) {
		return postService.createPostAddAll(post);
	}
	
	@PostMapping("/api/post/my")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PostDto createpost(@Valid @RequestBody PostDto postDto) {
		return postService.createPost(postDto);
		
	}
	
	// without using DTO class
	@PostMapping("/api/post/my/post")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Post createpostUsingPost(@Valid @RequestBody Post post) {
		return postService.createPostUsingPost(post);
		
	}
	
	//get all Post
	@GetMapping("/api/post/getAll")
	public List<PostDto> getAllPost() {
		return postService.getAllPost();
	}
	
	//get post by id
	@GetMapping("/api/post/get/{id}")
	public PostDto getPostById(@PathVariable int id) {
		return postService.getPostById(id);
	}
	
	//update post by id
	// @Valid - for implementing validation hibernate validator
	@PutMapping("/api/post/update/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public PostDto updatePostById(@Valid @RequestBody PostDto postDto , @PathVariable int id) {
		return postService.updatePost(postDto,id);
	}
	
	@DeleteMapping("/api/post/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public String deletedpostById(@PathVariable int id) {
		return postService.deletePost(id);
	}
	
	@GetMapping("/api/post/getAllByPaging")
	public List<PostDto> getAllByPaging(
			@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
		return postService.getAllPostWithPaging(pageNo, pageSize);
	}
	
	@GetMapping("/api/post/getAllByCustomPaging")
	public PageResponse getAllByCustomPaging(
			@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
		return postService.getAllPostWithCustomPaging(pageNo, pageSize);	
	}
	
	@GetMapping("/api/post/getAllByCustomPagingAndSorting")
	public PageResponse getAllPostByPagingAndSorting(
			@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pagesize,
			@RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy){
		return postService.getAllPostWithPagingAndSorting(pageNo, pagesize, sortBy);
	}

	@GetMapping("/api/post/getAllPostBySorting")
	public PageResponse getAllPostBySorting(
			@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NO, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pagesize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
		return postService.getAllPostWithSorting(pageNo, pagesize, sortBy, sortDir);
	}
	
	@GetMapping("/api/post/catagory/{id}")
	public ResponseEntity<List<PostDto>> getPostByCatagory(@PathVariable("id") long catagoryId) {

		List<PostDto> result = postService.getPostByCatagory(catagoryId);

		return new ResponseEntity<List<PostDto>>(result, HttpStatus.OK);

	}
 }
