package com.wisely.ch8_4.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wisely.ch8_4.Entity.Person;
import com.wisely.ch8_4.Repository.PersonRepository;
import com.wisely.ch8_4.service.DemoService;

@Service
public class DemoServiceImpl implements DemoService {
	/** 8.4:1.可以直接注入我们 的RersonRepository的Bean。 */
	@Autowired
	PersonRepository personRepository;
	
	/** 8.4:2.使用  */
	@Transactional(rollbackFor = {IllegalArgumentException.class})
	public Person savePersonWithRollBack(Person person) {
		Person p = personRepository.save(person);
		
		/** 8.4:3.硬编码手动触发异常。 */
		if (person.getName().equals("汪云飞")) {
			throw new IllegalArgumentException("汪云飞已存在，数据将回滚。");
		}
		
		return p;
	}

	/** 8.4:4.使用@Transactional注解的noRollbackFor属性，指定特定异常时，数据回滚。 */
	@Transactional(noRollbackFor = {IllegalArgumentException.class})
	public Person savePersonWithoutRollBack(Person person) {
		Person p = personRepository.save(person);
		
		if (person.getName().equals("汪云飞")) {
			throw new IllegalArgumentException("汪云飞虽已存在，数据将不会回滚。");
		}
		
		return p;
	}
	
}
