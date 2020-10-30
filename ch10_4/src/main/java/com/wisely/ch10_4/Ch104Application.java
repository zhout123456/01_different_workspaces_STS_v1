package com.wisely.ch10_4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wisely.ch10_4.dao.PersonRepository;
import com.wisely.ch10_4.domain.Person;

@SpringBootApplication
@RunWith(SpringJUnit4ClassRunner.class)
/** 10_4：1.使用@SpringApplicationConfiguration替代@ContextConfiguration来配置Spring Boot的Application Context。 */
@SpringApplicationConfiguration(classes = Ch104Application.class)
@WebAppConfiguration
/** 10_4：2.使用@Transactional注解，确保每次测试后的数据 将会被回滚。 */
@Transactional
public class Ch104Application {
	@Autowired
	PersonRepository personRepository;
	
	MockMvc  mvc;
	
	@Autowired
	WebApplicationContext webApplicationContext;
	
	String expectedJson;
	
	/** 10_4：3.使用Junit的@Before注解可在测试开始前进行一些初始化的工作。 */
	@Before
	public void setUp() throws JsonProcessingException {
		Person p1 = new Person("wyf");
		Person p2 = new Person("wisely");
		
		personRepository.save(p1);
		personRepository.save(p2);
		
		/** 10_4：4.获得期待返回的JSON字符串。 */
		expectedJson = Obj2Json(personRepository.findAll());
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	/** 10_4：5.将对象转换成JSON字符串。 */
	protected String Obj2Json(Object obj) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		
		return mapper.writeValueAsString(obj);
	}

	@Test
	public void testPersonController() throws Exception {
		String uri = "/person";
		/** 10_4：6.获得一个request的执行结果。 */
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)).andReturn();
		
		/** 10_4：7.获得request执行结果的状态。 */
		int status = result.getResponse().getStatus();
		/** 10_4：8.获得request执行结果的内容。 */
		String content = result.getResponse().getContentAsString();
		
		/** 10_4：9.将预期状态200和实际状态比较。 */
		Assert.assertEquals("错误，正确的返回值为200", 200, status);
		/** 10_4：10.将预期字符串和返回字符串比较。 */
		Assert.assertEquals("错误，返回值和预期返回值不一致 ", expectedJson, content);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(Ch104Application.class, args);
	}

}
