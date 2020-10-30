package com.wisely.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
/** ch12：1.使用@EnableConfigServer开启配置服务器的支持。 */
@EnableConfigServer
/** ch12：2.使用@EnableEurekaClient开启作为Eureka Server的客户端的支持。 */
@EnableEurekaClient
public class ConfigApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ConfigApplication.class, args);
	}

}
