package com.wisely.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableEurekaClient
/** ch12：1.通过@EnableFeignClients开启feign客户端支持。 */
@EnableFeignClients
/** ch12：2.通过@EnableCircuitBreaker开启CircuitBreaker的支持。 */
@EnableCircuitBreaker
/** ch12：3.通过@EnableZuulProxy开启网关代理的支持。 */
@EnableZuulProxy
public class UiApplication {
	public static void main(String[] args) {
		SpringApplication.run(UiApplication.class, args);
	}
}
