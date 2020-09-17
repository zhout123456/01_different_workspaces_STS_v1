package com.wisely.ch7_6.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			/** 第七章.Spring Boot的Web开发——7.6.WebSocket——7.6.3.实战——2.广播式：Spring Security的简单配置
			 * 1.设置Spring Security对/和/login路径不拦截。 */
			.antMatchers("/", "/login").permitAll()
			.anyRequest().authenticated()
			.and()
			.formLogin()
			/** 第七章.Spring Boot的Web开发——7.6.WebSocket——7.6.3.实战——2.广播式：Spring Security的简单配置
			 * 2.设置Spring Security的登录页面访问的路径为/login。 */
			.loginPage("/login")
			/** 第七章.Spring Boot的Web开发——7.6.WebSocket——7.6.3.实战——2.广播式：Spring Security的简单配置
			 * 3.登录成功后转向/chat路径。  */
			.defaultSuccessUrl("/chat")
			.permitAll()
			.and()
			.logout()
			.permitAll();
	}
	
	/** 第七章.Spring Boot的Web开发——7.6.WebSocket——7.6.3.实战——2.广播式：Spring Security的简单配置
	 * 4.在内存中分别配置两个用户wyf和wisely，密码和用户名一致，角色是USER。  */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.withUser("wyf").password("wyf").roles("USER")
			.and()
			.withUser("wisely").password("wisely").roles("USER");
	}
	
	/** 第七章.Spring Boot的Web开发——7.6.WebSocket——7.6.3.实战——2.广播式：Spring Security的简单配置
	 * 5./resources/static/目录下的静态资源，Spring Security不拦截。 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/static/**");
	}
	
	
}
