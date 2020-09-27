package com.wisely.ch8_2_M2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wisely.ch8_2_M2.service.testService;

@RestController
public class testController {

	@Autowired
	private testService test;
	
	@RequestMapping("/index")
	public List login(@RequestParam("id") Integer id){
		
		
		return test.getList();
	}

}
