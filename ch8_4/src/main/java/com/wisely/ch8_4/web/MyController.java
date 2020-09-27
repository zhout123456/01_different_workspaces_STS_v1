package com.wisely.ch8_4.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wisely.ch8_4.Entity.Person;
import com.wisely.ch8_4.service.DemoService;

@RestController
public class MyController {
	@Autowired
	DemoService demoService;
	
	/** 8.4:1.测试回滚情况。 */
	@RequestMapping("/rollback")
	public Person rollback(Person person) {
		return demoService.savePersonWithRollBack(person);
	}
	
	/** 8.4:2.测试不回滚情况 。*/
	@RequestMapping("/norollback")
	public Person noRollback(Person person) {
		return demoService.savePersonWithoutRollBack(person);
	}
}
