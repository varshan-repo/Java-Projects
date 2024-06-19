package com.springboot.blog.demospringbootblogrestapi.security;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springboot.blog.demospringbootblogrestapi.Entity.User;
import com.springboot.blog.demospringbootblogrestapi.repository.UserRepository;

// implements userDetailsService interface from spring security package

@Service
public class CustomUserDetailsService implements UserDetailsService{

	private UserRepository userRepository;

	@Autowired
	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
		User user = userRepository.findByUserNameOrEmail(usernameOrEmail, usernameOrEmail)
		.orElseThrow(()-> new UsernameNotFoundException("Username or email is not found: "+usernameOrEmail));
		
		Set<GrantedAuthority> authorities = user.getRoles()
				.stream()
				.map((role)-> new SimpleGrantedAuthority(role.getName()))
				.collect(Collectors.toSet());
		
		return new org.springframework.security.core.userdetails.User(user.getEmail(), 
				user.getPassword(), authorities);
	}

}
