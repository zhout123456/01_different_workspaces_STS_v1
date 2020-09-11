package com.wisely.ch7_6.web;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.wisely.ch7_6.domain.WiselyMessage;
import com.wisely.ch7_6.domain.WiselyResponse;

@Controller
public class WsController {
	
	/** 第七章.Spring Boot的Web开发——7.6.WebSocket——7.6.3.实战——2.广播式：
	 * 1.当浏览器想服务端发送请求时，通过@MessageMapping映射/welcome这个地址，类似于@RequestMapping。
	 * */
	@MessageMapping("/welcome")
	/**
	 * 2.当服务器端有消息时，会对订阅了@SendTo中的路径的浏览器发送消息。
	 * */
	@SendTo("/topic/getResponse")
	public WiselyResponse say(WiselyMessage message) throws Exception {
		Thread.sleep(3000);
		return new WiselyResponse("Welcome," +message.getName() +"!");
	}

}
