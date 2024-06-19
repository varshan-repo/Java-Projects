package com.springboot.blog.demospringbootblogrestapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.blog.demospringbootblogrestapi.Entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

	  List<Post> findByCatagoryId(long catagoryId);
}
