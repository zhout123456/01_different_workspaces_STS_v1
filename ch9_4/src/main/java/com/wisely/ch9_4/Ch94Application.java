package com.wisely.ch9_4;

import static java.lang.System.getProperty;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.dsl.core.Pollers;
import org.springframework.integration.dsl.file.Files;
import org.springframework.integration.dsl.mail.Mail;
import org.springframework.integration.feed.inbound.FeedEntryMessageSource;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.scheduling.PollerMetadata;

import com.rometools.rome.feed.synd.SyndEntry;

@SpringBootApplication
public class Ch94Application {

	public static void main(String[] args) {
		SpringApplication.run(Ch94Application.class, args);
	}
	
	/** 9.4：本例的所有代码都在入口类中完成。 */
	/** 9.4：1.通过@value注解自动获得https://spring.io/blog.atom的资源。 */
	@Value("https://spring.io/blog.atom")
	Resource resource;
	
	/** 9.4：2.使用Fluent API和Pollers配置默认的轮询方式。 */
	@Bean(name = PollerMetadata.DEFAULT_POLLER)
	public PollerMetadata poller() {
		return Pollers.fixedRate(500).get();
	}
	
	/** 9.4：3.FeedEntryMessageSource实际为feed:inbound-channel-adapter，此处即构造feed的入站通道适配器作为数据输入。 */
	@Bean
	public FeedEntryMessageSource feedMessageSource() throws IOException {
		FeedEntryMessageSource messageSource = new FeedEntryMessageSource(resource.getURL(), "news");
		
		return messageSource;
	}
	
	/** 9.4：4.流程从from方法开始。 */
	@Bean
	public IntegrationFlow myFlow() throws IOException {
		return IntegrationFlows.from(feedMessageSource())
								/** 9.4：5.通过路由方法route来选择路由，消息体（payload）的类型为SyndEntry，作为判断条件的类型为String，
								 * 判断的值是通过 payload获得的分类（CateGroy）。 */
								.<SyndEntry, String> route(	
										/** 9.5:6.通过不同分类的值转向不同的消息通道，若分类为releases，则转向releasesChannel；
										 * 若分类为engineering，则转向engineeringChannel；若分类为news，则转向newsChannel。*/
										payload -> payload.getCategories().get(0).getName(),
										mapping -> mapping.channelMapping("releases", "releasesChannel")
															.channelMapping("engineering", "engineeringChannel")
																.channelMapping("news", "newsChannel")
															/** 9.5：7.通过 get方法获得 IntegrationFlow实体，配置为Spring的Bean */
															).get();
	}
	
	/** 9.4: releases流程*/
	@Bean
	public IntegrationFlow releasesFlow() {
		/**  9.4:1.从消息 通道releasesChannel开始获取数据。 */
		return IntegrationFlows.from(MessageChannels.queue("releasesChannel", 10))
								/**  9.4:2.使用transform方法进行数据转换。payload类型为SyndEntry，将其转换为字符串类型，并自定义数据的格式。 */
								.<SyndEntry, String>transform(
											payload -> "《" +payload.getTitle() +"》" +payload.getLink() +getProperty("line.separator"))
												.handle(Files.outboundAdapter(new File("e:/springblog"))
													.fileExistsMode(FileExistsMode.APPEND)
													.charset("UTF-8")
													.fileNameGenerator(message -> "releases.txt")
													.get())
								.get();
	}
	
	/** 9.4: engineering流程*/
	@Bean
	public IntegrationFlow engineeringFlow() {
		return IntegrationFlows.from(MessageChannels.queue("engineeringChannel", 10))
										.<SyndEntry, String> transform(
												payload -> "《" +payload.getTitle() +"》" +payload.getLink() +getProperty("line.separator"))
													.handle(Files.outboundAdapter(new File("e:/springblog"))
															.fileExistsMode(FileExistsMode.APPEND)
															.charset("UTF-8")
															.fileNameGenerator(message -> "engineering.txt")
															.get())
													.get();
											
	}
	
	/** 9.4: news流程*/
	@Bean
	public IntegrationFlow newsFlow() {
		return IntegrationFlows.from(MessageChannels.queue("newsChannel", 10))
										.<SyndEntry, String>transform(
												payload -> "《" +payload.getTitle() +"》" +payload.getLink() +getProperty("line.separator"))
													.enrichHeaders(
															Mail.headers()
																.subject("来自Spring的新闻")
																.to("zhout18368404560@126.com")
																.from("zhout18368404560@126.com"))
													
													.handle(Mail.outboundAdapter("smtp.126.com")
															.port(25)
															.protocol("smtp")
															.credentials("zhout18368404560", "VXDUKEHFKHMCKHRT")
															.javaMailProperties(p -> p.put("mail.debug", "false")), e -> e.id("smtpOut"))
													.get();
	}	
								
	
	
}





