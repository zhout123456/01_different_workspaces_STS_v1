package com.wisely.ch7_6.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
/** 第七章.Spring Boot的Web开发——7.6.WebSocket——7.6.3.实战——2.广播式：
 * 1. 通过@EnableWebSocketMessageBroker注解开启使用STOMP协议来传输基于代理（message broker）的消息，这时控制器支持
 * 使用@MessageMapping，就像使用@RequestMapping一样。*/
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

	/** 2.注册STOMP协议的节点（endpoint），并映射到指定的URL。 */
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		/** 3.注册一个STOMP的endpoint，并指定使用SockJS协议。 */
		registry.addEndpoint("/endpointWisely").withSockJS();
		
		/** 第七章.Spring Boot的Web开发——7.6.WebSocket——7.6.3.实战——2.广播式：配置WebSocket
		 * 1.注册一个名为/endpointChat的endpoint*/
		registry.addEndpoint("endpointChat").withSockJS();
	}
	
	/** 4.配置消息代理（Message Broker）。 */
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		/** 5.广播式应配置一个/topic消息代理。 */
		/** 第七章.Spring Boot的Web开发——7.6.WebSocket——7.6.3.实战——2.广播式：配置WebSocket
		 * 2.点对点式应增加一个/queue消息代理。*/
		registry.enableSimpleBroker("/topic", "/queue");
	}

}
