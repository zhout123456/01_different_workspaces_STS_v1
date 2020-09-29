package com.wisely.ch8_6_2.domain;



import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import com.wisely.ch8_6_2.dao.Person;

@Repository
public class PersonDao {
	
	/** 8.6.2:1.Spring Boot已为我们配置StringRedisTemplate,在此处可以直接注入。 */
	@Autowired
	StringRedisTemplate stringRedisTemplate;
	
	/** 8.6.2:3.可以使用@Resource注解指定stringRedisTemplate,可注入基于字符串的简单属性操作方法。 */
	@Resource(name="stringRedisTemplate")
	ValueOperations<String, String> valOpsStr;
	
	/** 8.6.2:2.Spring Boot已为我们配置RedisTemplate,在此处可以直接注入。 */
	@Autowired
	RedisTemplate<Object, Object> redisTemplate;
	
	/** 8.6.2:4.可以使用@Resource注解指定redisTemplate,可注入基于对象的简单属性操作方法。 */
	@Resource(name="redisTemplate")
	ValueOperations<Object, Object> valOps;

	/** 8.6.2:5.通过set方法，存储字符串类型。 */
	public void stringRedisTemplateDemo() {
		valOpsStr.set("xx", "yy");
	}
	
	/** 8.6.2:6.通过set方法，存储对象类型。 */
	public void save(Person person) {
		valOps.set(person.getId(), person);
	}
	
	/** 8.6.2:7.通过get方法，获取字符串。 */
	public String getString() {
		return valOpsStr.get("xx");
	}
	
	/** 8.6.2:8.通过get方法，获取对象。 */
	public Person getPerson() {
		return (Person) valOps.get("1");
	}
	
}
