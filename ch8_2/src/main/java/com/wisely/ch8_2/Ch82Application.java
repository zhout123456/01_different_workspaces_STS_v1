package com.wisely.ch8_2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.wisely.ch8_2.dao.PersonRepository;
import com.wisely.ch8_2.dao.PersonRepository2;
import com.wisely.ch8_2.support.CustomRepositoryFactoryBean;

@SpringBootApplication
@EnableJpaRepositories(repositoryFactoryBeanClass = CustomRepositoryFactoryBean.class)
public class Ch82Application {

	@Autowired
	PersonRepository personRepository;
	
	@Autowired
	PersonRepository2 personRepository2;
	
	public static void main(String[] args) {
		SpringApplication.run(Ch82Application.class, args);
	}

}
