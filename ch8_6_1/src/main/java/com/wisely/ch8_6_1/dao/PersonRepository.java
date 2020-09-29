package com.wisely.ch8_6_1.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.wisely.ch8_6_1.domain.Person;

public interface PersonRepository extends MongoRepository<Person, String> {
	/** 8.6.1:1.支持方法名查询。 */
	Person findByName(String name);
	
	/** 8.6.1:2.支持@Query查询，查询参数构造JSON字符串即可。 */
	@Query("{'age': ?0}")
	List<Person> withQueryFindByAge(Integer age);

}
