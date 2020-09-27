package com.wisely.ch8_4.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wisely.ch8_4.Entity.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
	
}
