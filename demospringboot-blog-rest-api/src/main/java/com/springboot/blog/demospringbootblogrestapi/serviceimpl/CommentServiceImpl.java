package com.springboot.blog.demospringbootblogrestapi.serviceimpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.springboot.blog.demospringbootblogrestapi.Entity.Comment;
import com.springboot.blog.demospringbootblogrestapi.Entity.Post;
import com.springboot.blog.demospringbootblogrestapi.exception.BlogAPIException;
import com.springboot.blog.demospringbootblogrestapi.exception.ResourceNotFoundException;
import com.springboot.blog.demospringbootblogrestapi.payload.CommentDto;
import com.springboot.blog.demospringbootblogrestapi.repository.CommentRepository;
import com.springboot.blog.demospringbootblogrestapi.repository.PostRepository;
import com.springboot.blog.demospringbootblogrestapi.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	private CommentRepository commentRepository;
	private PostRepository postRepository;
	private ModelMapper mapper;

	@Autowired
	public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper mapper) {
		super();
		this.commentRepository = commentRepository;
		this.postRepository = postRepository;
		this.mapper=mapper;
	}

	private Comment mapToEntity(CommentDto commentDto) {

//		Comment comment = new Comment();
//		comment.setId(commentDto.getId());
//		comment.setName(commentDto.getName());
//		comment.setEmail(commentDto.getEmail());
//		comment.setBody(commentDto.getBody());
//		return comment;
		
		Comment comment = mapper.map(commentDto, Comment.class); // mapped using mapper class
		return comment;
	}

	private CommentDto mapToDto(Comment comment) {

//		CommentDto commentDto = new CommentDto();
//		commentDto.setId(comment.getId());
//		commentDto.setName(comment.getName());
//		commentDto.setEmail(comment.getEmail());
//		commentDto.setBody(comment.getBody());
//		return commentDto;
		
		CommentDto commentDto = mapper.map(comment, CommentDto.class); // mapped using mapper class
		return commentDto;
	}

	@Override
	public CommentDto createComment(int postId, CommentDto commentDto) {

		Comment comment = mapToEntity(commentDto);

		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		comment.setPost(post);

		Comment newComment = commentRepository.save(comment);

		return mapToDto(newComment);
	}

	@Override
	public List<CommentDto> getCommentsById(int postId) {

		List<Comment> comments = commentRepository.findByPostId(postId);

		return comments.stream().map(comment -> mapToDto(comment)).toList();
	}

	@Override
	public CommentDto getComment(int postId, int commentId) {

		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));

		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
		}

		return mapToDto(comment);
	}

	@Override
	public CommentDto updateComment(int postId, int commentId, CommentDto commentDto) {

		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));

		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("comment", "id", commentId));

		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "comment does not belong to post");
		}

		comment.setName(commentDto.getName());
		comment.setEmail(commentDto.getEmail());
		comment.setBody(commentDto.getBody());

		Comment updateComment = commentRepository.save(comment);

		return mapToDto(updateComment);
	}

	@Override
	public String deleteComment(int commentId) {

		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("comment", "id", commentId));
		commentRepository.delete(comment);
		return "Succesfully Deleted Comment: " + commentId;
	}

}
