package com.qa.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.qa.demo.persistence.domain.Dog;
import com.qa.demo.services.DogService;

@SpringBootApplication
public class SpringDemoApplication {

	public static void main(String[] args) {
		// bean bag
		ApplicationContext ac = SpringApplication.run(SpringDemoApplication.class, args);
		// DEMONSTRATIVE PURPOSES ONLY
		DogService service = ac.getBean(DogService.class);

		service.createDog(new Dog(null, "Shiba Inu", 12, "brown", "M"));

		System.out.println(service.getDogs());

		service.deleteDogById(1L);

		System.out.println(service.getDogs());
	}

}
