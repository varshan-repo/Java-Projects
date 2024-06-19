package com.springboot.blog.demospringbootblogrestapi.serviceimpl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.blog.demospringbootblogrestapi.Entity.Role;
import com.springboot.blog.demospringbootblogrestapi.Entity.User;
import com.springboot.blog.demospringbootblogrestapi.exception.BlogAPIException;
import com.springboot.blog.demospringbootblogrestapi.payload.LoginDto;
import com.springboot.blog.demospringbootblogrestapi.payload.RegisterDto;
import com.springboot.blog.demospringbootblogrestapi.repository.RoleRepository;
import com.springboot.blog.demospringbootblogrestapi.repository.UserRepository;
import com.springboot.blog.demospringbootblogrestapi.security.JwtTokenProvider;
import com.springboot.blog.demospringbootblogrestapi.service.AuthService;

// Using AuthenticationManager interface to implement UsernamePasswordAuthenticationToken

@Service
public class AuthServiceImpl implements AuthService{
	
	private AuthenticationManager authenticationManager;
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;
	private JwtTokenProvider jwtTokenProvider;

	public AuthServiceImpl(AuthenticationManager authenticationManager, 
			UserRepository userRepository, 
			RoleRepository roleRepository, 
			PasswordEncoder passwordEncoder,
			JwtTokenProvider jwtTokenProvider) {
		this.authenticationManager = authenticationManager;
		this.userRepository= userRepository;
		this.roleRepository=roleRepository;
		this.passwordEncoder=passwordEncoder;
		this.jwtTokenProvider= jwtTokenProvider;
	}

	@Override
	public String login(LoginDto loginDto) {
		
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
				(loginDto.getUserNameOrEmail(),loginDto.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token = jwtTokenProvider.generateToken(authentication);
		
		return token;
	}

	@Override
	public String register(RegisterDto registerDto) {

		// check whether userName exist in database for unique constraint
		if(userRepository.existsByUserName(registerDto.getUserName())) {
			throw new BlogAPIException(HttpStatus.BAD_GATEWAY, "User-Name already exists!!!");
		}
		
		// check whether email exist in database for unique constraint
		if(userRepository.existsByEmail(registerDto.getEmial())) {
			throw new BlogAPIException(HttpStatus.BAD_GATEWAY, "Email already exists!!!");
		}
		
		User user = new User();

		user.setName(registerDto.getName());
		user.setUserName(registerDto.getUserName());
		user.setEmail(registerDto.getEmial());
		user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

		Set<Role> roles = new HashSet<>();

		Role userRole = roleRepository.findByName("ROLE_USER").get();

		roles.add(userRole);

		user.setRoles(roles);

		userRepository.save(user);
		
		return "User registered successfully!!!";
	}

}
