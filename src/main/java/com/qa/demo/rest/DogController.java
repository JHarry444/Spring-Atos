package com.qa.demo.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<Dog> createDog(@RequestBody Dog dog) {
		return new ResponseEntity<Dog>(this.service.createDog(dog), HttpStatus.CREATED);
	}

	@GetMapping("/read")
	public ResponseEntity<List<Dog>> readDogs() {
		return ResponseEntity.ok(this.service.getDogs());
	}

	@GetMapping("/read/{id}")
	public ResponseEntity<Dog> readDog(@PathVariable Long id) {
		return ResponseEntity.ok(this.service.getDogByID(id));
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Dog> updateDog(@PathVariable Long id, @RequestBody Dog newData) {
		return new ResponseEntity<Dog>(this.service.updateDogById(id, newData), HttpStatus.ACCEPTED);

	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteDog(@PathVariable Long id) {
		boolean deleted = this.service.deleteDogById(id);

		if (deleted) {
			return ResponseEntity.ok(deleted);
		} else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
