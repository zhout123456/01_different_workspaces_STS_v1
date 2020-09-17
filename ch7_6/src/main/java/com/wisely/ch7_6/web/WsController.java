package com.wisely.ch7_6.web;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
	
	/** 第七章.Spring Boot的Web开发——7.6.WebSocket——7.6.3.实战——2.广播式：控制器：在WsController内添加如下代码：
	 * 1.通过SimpMessagingTemplate向浏览器发送消息。*/
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	/** 第七章.Spring Boot的Web开发——7.6.WebSocket——7.6.3.实战——2.广播式：控制器：在WsController内添加如下代码：
	 * 2.在Spring MVC中，可以直接在参数中获得principal，pinciple中包含当前用户的信息。 */
	@MessageMapping("/chat")
	public void handleChat(Principal principal, String msg) {
		/** 第七章.Spring Boot的Web开发——7.6.WebSocket——7.6.3.实战——2.广播式：控制器：在WsController内添加如下代码：
		 * 3.这里是一段硬代码，如果发送人是wyf，则发送给wisely；如果发送人是wisely，则发送给wyf，读者可以
		 * 根据项目实际需要改写此处代码。 */
		if (principal.getName().equals("wyf")) {
			/** 第七章.Spring Boot的Web开发——7.6.WebSocket——7.6.3.实战——2.广播式：控制器：在WsController内添加如下代码：
			 * 4.通过messagingTemplate.convertAndSendToUser向用户发送消息，第一个参数是接受消息的用户，
			 * 第二个是浏览器订阅的地址，第三个是消息本身。 */
			simpMessagingTemplate.convertAndSendToUser("wisely", 
														"/queue/notifications", 
														principal.getName() +"-send:" +msg);
		} else {
			simpMessagingTemplate.convertAndSendToUser("wyf",
														"/queue/notifications",
														principal.getName() +"-send:" +msg);
		}
	}

}
