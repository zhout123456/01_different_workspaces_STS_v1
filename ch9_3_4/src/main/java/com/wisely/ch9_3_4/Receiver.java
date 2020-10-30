package com.wisely.ch9_3_4;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {
	
	/** 9.3.4：1.@JmsListener是Spring4.1为我们提供的一个新特性，用来简化JMS开发。我们只需要在
	 * 这个注解的属性destination指定要监听的目的地，即可接收该目的地发送的消息。此例监听my-destination
	 * 目的地发送的消息。 */
	@JmsListener(destination = "my-destination")
	public void receiveMessage(String message) {
		System.out.println("Receiver接受到的消息：<" +message +">");
	}

}
