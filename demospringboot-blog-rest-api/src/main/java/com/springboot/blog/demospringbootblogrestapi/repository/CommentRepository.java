package com.springboot.blog.demospringbootblogrestapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.blog.demospringbootblogrestapi.Entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

	List<Comment> findByPostId(int postId);
	
}
