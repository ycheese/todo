package com.example.todo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.filter.CorsFilter;

import com.example.todo.security.JwtAuthenticationFilter;

import lombok.extern.slf4j.Slf4j;

@EnableWebSecurity
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// http 시큐리티 빌더
		http.cors()
		.and()
		.csrf()
			.disable()
		.httpBasic()
			.disable()
		.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.authorizeRequests()
			.antMatchers("/", "/auth/**").permitAll()
		.anyRequest()
			.authenticated();
		
		// filter 등록
		// 매 요청마다 CorsFilter 실행 후 jwtAuthenticationFilter 실행
		http.addFilterAfter(jwtAuthenticationFilter, CorsFilter.class);
	}
}
