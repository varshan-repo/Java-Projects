package com.springboot.blog.demospringbootblogrestapi.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.demospringbootblogrestapi.payload.CommentDto;
import com.springboot.blog.demospringbootblogrestapi.service.CommentService;

@RestController
public class CommentController {

	private CommentService commentService;

	@Autowired
	public CommentController(CommentService commentService) {
		super();
		this.commentService = commentService;
	}

//		@PostMapping("/api/comment/createComment/{postid}")
//		public CommentDto createComment( @PathVariable int postid,
//				@RequestBody CommentDto commentDto) {
//			 return commentService.createComment(postid, commentDto);			
//		}
//		
	@PostMapping("/api/comment/createComment/{postid}")
	public ResponseEntity<CommentDto> createComment(@PathVariable int postid,  @RequestBody CommentDto commentDto) {
		return new ResponseEntity<>(commentService.createComment(postid, commentDto), HttpStatus.CREATED);
	}

	@GetMapping("/api/comment/getCommentById/{postId}")
	public ResponseEntity<List<CommentDto>> getCommentsById(@PathVariable int postId) {
		return new ResponseEntity<>(commentService.getCommentsById(postId), HttpStatus.FOUND);
	}

	@GetMapping("/api/comment/getComment/{commentId}/post/{postId}")
	public ResponseEntity<CommentDto> getComment(@PathVariable int commentId, @PathVariable int postId) {

		return new ResponseEntity<CommentDto>(commentService.getComment(postId, commentId), HttpStatus.FOUND);
	}

	@PutMapping("/api/comment/updateComment/{commentId}/post/{postId}")
	public ResponseEntity<CommentDto> updateComment(@PathVariable int commentId, @PathVariable int postId,
			 @RequestBody CommentDto commentDto) {
		
		return new ResponseEntity<CommentDto>(commentService.updateComment(postId, commentId, commentDto),HttpStatus.CREATED);
	}

	@DeleteMapping("/api/comment/deleteComment/{commentId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deleteComment(@PathVariable int commentId) {
		return new ResponseEntity<String>(commentService.deleteComment(commentId),HttpStatus.OK);
	}
}
