package com.wisely.ch9_3_4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.core.JmsTemplate;

/** 9.3.4：1.Spring Boot为我们提供了CommandLineRunner接口，用于程序启动哪那个后执行的代码，
 * 通过重写其run方法执行。 */
@SpringBootApplication
public class Ch934Application implements CommandLineRunner {
	
	/** 9.3.4：2.注入Spring Boot为我们配置好的JmsTemplate的Bean。 */
	@Autowired
	JmsTemplate jmsTemplate; 
	
	public static void main(String[] args) {
		SpringApplication.run(Ch934Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		/** 9.3.4：3.通过JmsTemplate的send方法向my-destination目的地发送 Msg的消息，这里
		 * 也等于在消息代理上定义了一个目的地叫my-destination。 */
		jmsTemplate.send("my-destination", new Msg());
	}

}
