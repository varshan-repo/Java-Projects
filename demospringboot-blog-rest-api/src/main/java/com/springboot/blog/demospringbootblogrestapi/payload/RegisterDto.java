package com.springboot.blog.demospringbootblogrestapi.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {

	private String name;
	private String userName;
	private String emial;
	private String password;

}
