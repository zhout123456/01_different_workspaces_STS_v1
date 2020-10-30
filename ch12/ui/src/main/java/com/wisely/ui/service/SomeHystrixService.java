package com.wisely.ui.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

/** ch12：在Spring Boot下使用Ribbon，我们只需注入一个RestTemplate即可，Spring Boot已为
 * 我们最好了配置。 使用@HystrixCommand的fallbackMethod参数指定，当本方法调用失败时调用
 * 后备方法fallbackSome。 */
@Service
public class SomeHystrixService {

	@Autowired
	RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod = "fallbackSome")
	public String getSome() {
		return restTemplate.getForObject("http://some/getsome", String.class);
	}
	
	public String fallbackSome() {
		return "some service 模块故障";
	}
	
}
