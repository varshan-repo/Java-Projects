package com.springboot.blog.demospringbootblogrestapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.demospringbootblogrestapi.payload.JWTAuthResponse;
import com.springboot.blog.demospringbootblogrestapi.payload.LoginDto;
import com.springboot.blog.demospringbootblogrestapi.payload.RegisterDto;
import com.springboot.blog.demospringbootblogrestapi.service.AuthService;

@RestController
public class AuthController {

	private AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/api/auth/login")
	public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto) {

		String token = authService.login(loginDto);
		
		JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
		
		jwtAuthResponse.setAccessToken(token);

		return ResponseEntity.ok(jwtAuthResponse);
	}

	@PostMapping("/api/auth/register")
	public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {

		String responseString = authService.register(registerDto);

		return new ResponseEntity<String>(responseString, HttpStatus.CREATED);
	}
}
