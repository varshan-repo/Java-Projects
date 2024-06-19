package com.springboot.blog.demospringbootblogrestapi.security;

import java.io.IOException;

import org.aspectj.weaver.NewConstructorTypeMunger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// @component has to be included
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	private JwtTokenProvider jwtTokenProvider;
	
	private CustomUserDetailsService customUserDetailsService;
	
	public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider,
			CustomUserDetailsService customUserDetailsService) {
		this.jwtTokenProvider = jwtTokenProvider;
		this.customUserDetailsService = customUserDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, 
			HttpServletResponse response, 
			FilterChain filterChain)
			throws ServletException, IOException {
		
		// get JWT token from http request
		String token = getTokenFromRequest(request);
 		
		// validate token from request
		if(StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
			
			//get username from token
			String username = jwtTokenProvider.getUsername(token);
			
			//load the user associated with token
			UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
			
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, 
					null, 
					userDetails.getAuthorities());
			
			authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		}
		
		filterChain.doFilter(request, response);
	}
	
	private String getTokenFromRequest(HttpServletRequest request) {
		
		String bearertoken =  request.getHeader("Authorization");
		
		// to get only token not with "Bearer" keyWord
		// if checks if bearerToken is present and starts with Bearer
		if(StringUtils.hasText(bearertoken) && bearertoken.startsWith("Bearer ")) {
			return bearertoken.substring(7, bearertoken.length());
		}
		return null;
	}

}
