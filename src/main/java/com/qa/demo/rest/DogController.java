package com.qa.demo.rest;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.qa.demo.persistence.domain.Dog;
import com.qa.demo.services.DogService;

//@Controller
@RestController // automatically converts data to json
public class DogController {

	private DogService service;

	public DogController(DogService service) {
		super();
		this.service = service;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/hello")
	public String test() {
		return "Hello, World!";
	}

	@PostMapping("/create")
	public Dog createDog(@RequestBody Dog dog) {
		return this.service.createDog(dog);
	}

	@GetMapping("/read")
	public List<Dog> readDogs() {
		return this.service.getDogs();
	}

	@GetMapping("/read/{id}")
	public Dog readDog(@PathVariable Long id) {
		return this.service.getDogByID(id);
	}

	@PutMapping("/update/{id}")
	public Dog updateDog(@PathVariable Long id, @RequestBody Dog newData) {
		return this.service.updateDogById(id, newData);
	}

	@DeleteMapping("/delete/{id}")
	public boolean deleteDog(@PathVariable Long id) {
		return this.service.deleteDogById(id);
	}
}
