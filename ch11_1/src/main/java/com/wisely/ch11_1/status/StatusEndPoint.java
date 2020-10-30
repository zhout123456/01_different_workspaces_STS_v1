package com.wisely.ch11_1.status;

import org.springframework.beans.BeansException;
import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/** 11.1：1.通过@ConfigurationProperties的设置，我们可以在application.properties中通过
 * endpoints.status配置我们的端点。 */
@ConfigurationProperties(prefix = "endpoints.status", ignoreUnknownFields = false)
/** 11.1：2.继承AbstractEndpoint类，AbstractEndpoint是Endpoint接口的抽象实现，当前类一定要
 * 重写invoke方法。实现ApplicationContextAware接口可以让当前类对 Spring容器的资源有意识，即可
 * 访问容器的资源。 */
public class StatusEndPoint extends AbstractEndpoint<String> implements ApplicationContextAware {
	
	ApplicationContext context;
	
	public StatusEndPoint() {
		super("status");
	}
	
	/** 11.1：3.通过重写invoke方法，返回我们要监控的内容。 */
	@Override
	public String invoke() {
		StatusService statusService = context.getBean(StatusService.class);
		
		return "The Current Status is :" +statusService.getStatus();
	}
	
	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		this.context = arg0;
	}
	
}
