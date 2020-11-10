package com.qa.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.qa.demo.persistence.domain.Dog;
import com.qa.demo.persistence.repo.DogRepo;

@SpringBootApplication
public class SpringDemoApplication {

	public static void main(String[] args) {
		// bean bag
		ApplicationContext ac = SpringApplication.run(SpringDemoApplication.class, args);
		// DEMONSTRATIVE PURPOSES ONLY
		DogRepo repo = ac.getBean(DogRepo.class);
		repo.save(new Dog());
	}

}
