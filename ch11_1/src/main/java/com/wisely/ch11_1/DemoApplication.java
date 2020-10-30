package com.wisely.ch11_1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wisely.ch11_1.status.StatusEndPoint;
import com.wisely.ch11_1.status.StatusService;

@SpringBootApplication
@RestController
public class DemoApplication {
	@Autowired
	StatusService statusService;
	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	/** 11.1：1.注册端点的Bean。 */
	@Bean
	public Endpoint<String> status() {
		Endpoint<String> status = new StatusEndPoint();
		
		return status;
	}
	
	/** 11.1：2.定义 控制器方法用来改变status。 */
	@RequestMapping("/change")
	public String changeStatus(String status) {
		statusService.setStatus(status);
		
		return "OK";
	}
	
}
