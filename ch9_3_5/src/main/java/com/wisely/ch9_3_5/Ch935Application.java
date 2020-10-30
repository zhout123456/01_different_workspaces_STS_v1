package com.wisely.ch9_3_5;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Ch935Application implements CommandLineRunner {
	/** 9.3.5：1.可注入Spring Boot为我们自动配置好的RabbitTemplate。 */
	@Autowired
	RabbitTemplate rabbitTemplate;
	
	public static void main(String[] args) {
		SpringApplication.run(Ch935Application.class, args);
	}

	/** 9.3.5：2.定义目的地即队列，队列名称为my-queue。 */
	@Bean
	public Queue wiselyQueue() {
		return new Queue("my-queue");
	}
	
	@Override
	public void run(String... args) throws Exception {
		/** 9.3.5：3.通过RabbitTemplate的convertAndSend方法向队列my-queue发送消息。 */
		rabbitTemplate.convertAndSend("my-queue", "来自RabbitMQ的问候");
		
	}

}
