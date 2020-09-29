package com.wisely.ch8_6_1.domain;

import java.util.Collection;
import java.util.LinkedHashSet;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/** 8.6.1 非关系型数据库NoSQL——MongDB：1.@Document注解映射领域模型和MongoDB的文档。 */
@Document
public class Person {
	/** 8.6.1 非关系型数据库NoSQL——MongDB：2.@Id注解表明这个属性为文档的Id。 */
	@Id
	private String id;
	private String name;
	private Integer age;
	
	
	/** 8.6.1 非关系型数据库NoSQL——MongDB：3.@Field注解此属性在文档中的名称为locs，locations属性将以数组形式
	 * 存在当前数据记录中。 */
	@Field("locs")
	private Collection<Location> locations = new LinkedHashSet<Location>();
	
	public Person(String name, Integer age) {
		super();
		this.name = name;
		this.age = age;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Collection<Location> getLocations() {
		return locations;
	}
	public void setLocations(Collection<Location> locations) {
		this.locations = locations;
	}
	
	
	
}
