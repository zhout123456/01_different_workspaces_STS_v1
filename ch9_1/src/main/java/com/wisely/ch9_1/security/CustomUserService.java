package com.wisely.ch9_1.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.wisely.ch9_1.dao.SysUserRepository;
import com.wisely.ch9_1.domain.SysUser;

/** 9.1：1.自定义 需实现UserDetailsService接口。 */
public class CustomUserService implements UserDetailsService {
	@Autowired
	SysUserRepository userRepository;
	
	/** 9.1：2.重写loadUserByUsername方法获得用户。 */
	@Override
	public UserDetails loadUserByUsername(String username) {
		SysUser user = userRepository.findByUsername(username);
		
		if (user == null) {
			throw new UsernameNotFoundException("用户名不存在");
		}
		
		/** 9.1：3.我们当前的用户实现了UserDetails接口，可直接返回给Spring Security使用。 */
		return user;
	}
	
}
