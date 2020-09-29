package com.wisely.ch8_6_2.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wisely.ch8_6_2.dao.Person;
import com.wisely.ch8_6_2.domain.PersonDao;

@RestController
public class DataController {
	
	@Autowired
	PersonDao personDao;
	
	/** 8.6.2:1.演示设置字符及对象。 */
	@RequestMapping("/set")
	public void set() {
		Person person = new Person("1", "wyf", 32);
		personDao.save(person);
		personDao.stringRedisTemplateDemo();
	}
	
	/** 8.6.2:2.演示获得字符。 */
	@RequestMapping("/getStr")
	public String getStr() {
		return personDao.getString();
	}
	
	/** 8.6.2:3.演示获得对象。 */
	@RequestMapping("/getPerson")
	public Person getPerson() {
		return personDao.getPerson();
	}
	
}
