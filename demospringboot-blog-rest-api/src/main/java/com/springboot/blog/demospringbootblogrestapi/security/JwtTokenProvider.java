package com.springboot.blog.demospringbootblogrestapi.security;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.springboot.blog.demospringbootblogrestapi.exception.BlogAPIException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/*
	@Value -> is from bean.factory package used to assign default values 
	here we use default values from application.properties file using ${}
*/

@Component
public class JwtTokenProvider {

	@Value("${app.jwt-secret}")
	private String jwtSecret;

	@Value("${app-jwt-expiration-milliseconds}")
	private long jwtExpirationDate;

	// generate JWT token
	public String generateToken(Authentication authentication) {
		String username = authentication.getName();

		Date currentDate = new Date();

		Date expirationDate = new Date(currentDate.getTime() + jwtExpirationDate);
		
		String tokenString = Jwts.builder()
				.setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(expirationDate)
				.signWith(key())
				.compact();

		return tokenString;
	}

	public Key key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	}
	
	//get username from JWt token
	
	public String getUsername(String token) {

		Claims claims = Jwts.parser()
				.setSigningKey(key())
				.build()
				.parseClaimsJws(token)
				.getBody();

		String usernameString = claims.getSubject();

		return usernameString;

	}
	
	//validate jwt token
	
	public boolean validateToken(String token) {
		try {
			Jwts.parser()
			.setSigningKey(key())
			.build()
			.parse(token);

			return true;
			
		} catch (MalformedJwtException ex) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Invalid JWT token!!!");
		} catch (ExpiredJwtException ex) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Expired JWT Token!!!");
		} catch (UnsupportedJwtException ex) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Unsupported JWT token!!!");
		} catch (IllegalArgumentException ex) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "JWT claims String is empty");
		}
		
	}
}
