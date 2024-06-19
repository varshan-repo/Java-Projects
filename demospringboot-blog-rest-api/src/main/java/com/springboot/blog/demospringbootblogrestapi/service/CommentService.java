package com.springboot.blog.demospringbootblogrestapi.service;

import java.util.List;

import com.springboot.blog.demospringbootblogrestapi.payload.CommentDto;

public interface CommentService {

	CommentDto createComment(int postId, CommentDto commentDto);
	
	List<CommentDto> getCommentsById(int postId);
	
	CommentDto getComment(int postId, int commentId);
	
	CommentDto updateComment(int postId, int commentId, CommentDto commentDto);
	
	String deleteComment(int commentId);
}
