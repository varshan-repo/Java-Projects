package com.springboot.blog.demospringbootblogrestapi.service;

import com.springboot.blog.demospringbootblogrestapi.payload.LoginDto;
import com.springboot.blog.demospringbootblogrestapi.payload.RegisterDto;

public interface AuthService {

	 String login(LoginDto loginDto);
	 
	 String register(RegisterDto registerDto);
}
