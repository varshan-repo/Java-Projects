package com.springboot.blog.demospringbootblogrestapi.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.springboot.blog.demospringbootblogrestapi.Entity.Catagory;
import com.springboot.blog.demospringbootblogrestapi.Entity.Post;
import com.springboot.blog.demospringbootblogrestapi.exception.ResourceNotFoundException;
import com.springboot.blog.demospringbootblogrestapi.payload.PageResponse;
import com.springboot.blog.demospringbootblogrestapi.payload.PostDto;
import com.springboot.blog.demospringbootblogrestapi.repository.CatagoryRepository;
import com.springboot.blog.demospringbootblogrestapi.repository.PostRepository;
import com.springboot.blog.demospringbootblogrestapi.service.PostService;

@Service
public class PostServiceImpl implements PostService {

	private PostRepository postRepository;
	private ModelMapper mapper;
	private CatagoryRepository catagoryRepository;

	@Autowired
	public PostServiceImpl(PostRepository postRepository, ModelMapper mapper, CatagoryRepository catagoryRepository) {
		this.postRepository = postRepository;
		this.mapper=mapper;
		this.catagoryRepository=catagoryRepository;
	}

	@Override
	public PostDto createPost(PostDto postDto) {
		
		Catagory catagory = catagoryRepository.findById(postDto.getCatagoryId())
		.orElseThrow(()-> new ResourceNotFoundException("catagory", "id", postDto.getCatagoryId()));
		
		Post post = mapToEntity(postDto); // calling the private method mapTOEntity
		post.setCatagory(catagory);
		Post newPost = postRepository.save(post);

		PostDto postResponse = mapToDto(newPost); // calling the private method mapToDto
		return postResponse;

	}

	// this is wriiten without converting post to dto
	@Override
	public Post createPostUsingPost(Post post) {

		Post response = postRepository.save(post);
		return response;
	}

	@Override
	public List<PostDto> getAllPost() {
		List<Post> allPost = postRepository.findAll();
		List<PostDto> allPostDto = allPost.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
		return allPostDto;
	}

	@Override
	public PostDto getPostById(int id) {

		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		return mapToDto(post);
	}

	@Override
	public PostDto updatePost(PostDto postDto, int id) {

		Post post = postRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

		Catagory catagory = catagoryRepository.findById(postDto.getCatagoryId())
				.orElseThrow(() -> new ResourceNotFoundException("catagory", "id", postDto.getCatagoryId()));

		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setDescription(postDto.getDescription());
		post.setCatagory(catagory);
		Post updatedPost = postRepository.save(post);

		return mapToDto(updatedPost);

	}
	
	@Override
	public String deletePost(int id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		postRepository.delete(post);
		return "deleted";
	}
	
	@Override
	public List<Post> createPostAddAll(List<Post> post) {
		
		List<Post> allPost = postRepository.saveAll(post);
		
		return allPost;
	}
	
	@Override
	public List<PostDto> getAllPostWithPaging(int pageNo, int pageSize) {
		
		// create instance of pageable
		PageRequest pageable = PageRequest.of(pageNo, pageSize);

		//findall() method in pageable class not in jpaRepository class
		Page<Post> allPost = postRepository.findAll(pageable); 

		// get content from page<post> used to convert page<post> to list<post>
		List<Post> pagePost = allPost.getContent();
		
		return pagePost.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
	}
	
	@Override
	public PageResponse getAllPostWithCustomPaging(int pageNo, int pageSize) {
		
		PageRequest pageable = PageRequest.of(pageNo, pageSize);
		
		Page<Post> allPost = postRepository.findAll(pageable);
		
		List<Post> getPages = allPost.getContent();	
		
		List<PostDto> filteredGetPages = getPages.stream().map(post-> mapToDto(post)).collect(Collectors.toList());
		
		// PageResponse DTO class 
		PageResponse pageResponse = new PageResponse();
		
		pageResponse.setContent(filteredGetPages);
		pageResponse.setPageNo(allPost.getNumber());
		pageResponse.setPageSize(allPost.getSize());
		pageResponse.setTotalElements(allPost.getNumberOfElements());
		pageResponse.setTotalPages(allPost.getTotalPages());
		pageResponse.setLast(allPost.isLast());
		
		return pageResponse;
	}
	
	@Override
	public PageResponse getAllPostWithPagingAndSorting(int pageNo, int pageSize, String sortBy) {

		PageRequest pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		
		Page<Post> allPost = postRepository.findAll(pageable);
		
		List<Post> post = allPost.getContent();
		
		List<PostDto> filteredPost = post.stream().map(posts -> mapToDto(posts)).collect(Collectors.toList());
		
		PageResponse pageResponse = new PageResponse();
		
		pageResponse.setContent(filteredPost);
		pageResponse.setPageNo(allPost.getNumber());
		pageResponse.setPageSize(allPost.getSize());
		pageResponse.setTotalElements(allPost.getNumberOfElements());
		pageResponse.setTotalPages(allPost.getTotalPages());
		pageResponse.setLast(allPost.isLast());
		
		return pageResponse;
	}
	
	@Override
	public PageResponse getAllPostWithSorting(int pageNo, int pageSize, String sortBy, String sortDir) {

		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		
		PageRequest pageable = PageRequest.of(pageNo, pageSize, sort);
		
		Page<Post> allpost = postRepository.findAll(pageable);
		
		List<Post> Post = allpost.getContent();
		
		List<PostDto> filteredPost= Post.stream().map(post1 -> mapToDto(post1)).collect(Collectors.toList());
		
		PageResponse pageResponse = new PageResponse();
		
		pageResponse.setContent(filteredPost);
		pageResponse.setPageNo(allpost.getNumber());
		pageResponse.setPageSize(allpost.getSize());
		pageResponse.setTotalElements(allpost.getNumberOfElements());
		pageResponse.setTotalPages(allpost.getTotalPages());
		pageResponse.setLast(allpost.isLast());
		
		return pageResponse;
	}

	@Override
	public List<PostDto> getPostByCatagory(long catagoryId) {

		Catagory catagory = catagoryRepository.findById(catagoryId)
		.orElseThrow(()-> new ResourceNotFoundException("catagory", "id", catagoryId));
		
		List<Post> posts = postRepository.findByCatagoryId(catagoryId);
		
		List<PostDto> postDto = posts.stream().map((post)-> mapToDto(post)).collect(Collectors.toList());
		
		return postDto;
	}

	// convert entity to dto
	private PostDto mapToDto(Post newPost) {
		
//		PostDto postResponse = new PostDto();
//		postResponse.setId(newPost.getId());
//		postResponse.setTitle(newPost.getTitle());
//		postResponse.setDescription(newPost.getDescription());
//		postResponse.setContent(newPost.getContent());
//		postResponse.setComments(newPost.getComments());
//		return postResponse;
		
		PostDto postDto = mapper.map(newPost, PostDto.class); // Done using Mapper Class
		return postDto;

	}

	// convert dto to entity
	private Post mapToEntity(PostDto postDto) {
		
//		Post post = new Post();
//		post.setId(postDto.getId());
//		post.setTitle(postDto.getTitle());
//		post.setDescription(postDto.getDescription());
//		post.setContent(postDto.getContent());
//		post.setComments(postDto.getComments());
//		return post;
		
		Post post = mapper.map(postDto, Post.class); // Done using Mapper Class
		return post;
	}

}
