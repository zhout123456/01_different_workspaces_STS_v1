package com.wisely.ch9_1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.wisely.ch9_1.security.CustomUserService;

/** 9.1:1.拓展Spring Security配置需继承WebSecurityConfigurerAdapter。 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	/** 9.1:2.注册CustomUserService的Bean。 */
	@Bean
	UserDetailsService customUserService() {
		return new CustomUserService();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws  Exception {
		/** 9.1:3.添加我们 自定义的user detail service认证。 */
		auth.userDetailsService(customUserService());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				/** 9.1:4.所有请求需要认证即登录后才能访问， */
				.anyRequest().authenticated()
				.and()
				.formLogin()
					.loginPage("/login")
					.failureUrl("/login?error")
					/** 9.1:5.定制登录行为，登录页面可任意访问。 */
					.permitAll()
				.and()
				/** 9.1:6.定制注销行为，注销请求可任意访问。 */
				.logout().permitAll();
			
	}
	
	
}
