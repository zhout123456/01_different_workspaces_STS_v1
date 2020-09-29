package com.wisely.ch8_5.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.wisely.ch8_5.dao.PersonRepository;
import com.wisely.ch8_5.domain.Person;
import com.wisely.ch8_5.service.DemoService;

@Service
public class DemoServiceImpl implements DemoService {
	@Autowired
	PersonRepository personRepository;

	/** 8.5数据缓存Cache:1.@CachePut缓存新增的或更新的数据到缓存，其中缓存名称为people,数据的
	 * key是person的id。 */
	@Override
	@CachePut(value = "people", key = "#person.id")
	public Person save(Person person) {
		Person p = personRepository.save(person);
		System.out.println("为id、key为：" +p.getId() +"数据做了缓存。");
		
		return p;
	}

	/** 8.5数据缓存Cache:2.@CacheEvict从缓存people中删除key为id的数据。 */
	@Override
	@CacheEvict(value = "people")
	public void remove(Long id) {
		System.out.println("删除了id、key为：" +id +"的数据缓存。");
//		personRepository.delete(id);
	}

	/** 8.5数据缓存Cache:3.@Cacheable缓存key为person的id数据到缓存people中。 */
	@Override
	@Cacheable(value = "people", key = "#person.id")
	public Person findOne(Person person) {
		Person p = personRepository.findOne(person.getId());
		System.out.println("为id、key为：" +p.getId() +"数据做了缓存。");
		
		return p;
	}
	
	/** 8.5数据缓存Cache: *特别说明：如果没有指定key，则方法参数作为key保存到缓存中。 */
}
