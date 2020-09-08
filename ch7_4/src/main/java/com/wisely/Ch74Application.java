package com.wisely;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Ch74Application {

	public static void main(String[] args) {
		SpringApplication.run(Ch74Application.class, args);
	}
	
	@RequestMapping("/hello")
	public String hello() {
		return "Hello world!";
	}
	
//	@Component
//	public static class CustomServletContainer implements EmbeddedServletContainerCustomizer {
//
//		@Override
//		public void customize(ConfigurableEmbeddedServletContainer container) {
//			container.setPort(8888);
//			container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/404.html"));
//			container.setSessionTimeout(10, TimeUnit.MINUTES);
//		}
//	}

}
