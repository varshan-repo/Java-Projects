package com.springboot.blog.demospringbootblogrestapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.springboot.blog.demospringbootblogrestapi.security.JwtAuthenticationEntryPoint;
import com.springboot.blog.demospringbootblogrestapi.security.JwtAuthenticationFilter;

/*
	@EnableMethodSecurity -> used to pre-authorize or post-authorize
	@Configuration ->
*/
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

	private UserDetailsService userDetailsService;
	
	private JwtAuthenticationEntryPoint authenticationEntryPoint;
	
	private JwtAuthenticationFilter authenticationFilter;
	

	public SecurityConfig(UserDetailsService userDetailsService, 
			JwtAuthenticationEntryPoint authenticationEntryPoint,
			JwtAuthenticationFilter authenticationFilter) {
		
		this.userDetailsService = userDetailsService;
		this.authenticationEntryPoint=authenticationEntryPoint;
		this.authenticationFilter=authenticationFilter;
	}

	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// this authentication manager implicitly takes UserDetailsService.class instance and PasswordEncoder.class instance for 
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.csrf().disable().authorizeHttpRequests((authorize) ->
		// authorize.anyRequest().authenticated()
		authorize.requestMatchers(HttpMethod.GET, "/api/**").permitAll()// get() methods can be accessed by all users
					.requestMatchers("/api/auth/**").permitAll()
					.anyRequest()
					.authenticated())
					.httpBasic(Customizer.withDefaults()
							).exceptionHandling((exception)-> exception
									.authenticationEntryPoint(authenticationEntryPoint)
									).sessionManagement((session)->session
											.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();

	}

	// In Memory authentication

//	@Bean
//	public UserDetailsService userDetailsService() {
//
//		UserDetails varshan = User.builder()
//				.username("varshan")
//				.password(passwordEncoder().encode("varshan"))
//				.roles("USER")
//				.build();
//
//		UserDetails admin = User.builder()
//				.username("admin")
//				.password(passwordEncoder().encode("admin"))
//				.roles("ADMIN")
//				.build();
//
//		return new InMemoryUserDetailsManager(varshan, admin);
//
//	}
}
