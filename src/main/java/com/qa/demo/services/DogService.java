package com.qa.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.qa.demo.persistence.domain.Dog;
import com.qa.demo.persistence.repo.DogRepo;

@Service
public class DogService {

	// CRUD - Create Read Update Delete
	private DogRepo repo;

	// Autowiring - Spring automatically inserts a dependency (dogRepo in this case)
	public DogService(DogRepo repo) {
		super();
		this.repo = repo;
	}

	public Dog createDog(Dog dog) {
		return this.repo.save(dog);
	}

	public Dog getDogByID(Long id) {
		Optional<Dog> dog = this.repo.findById(id);
		return dog.get();
//		Dog dog = this.repo.findById(id);
//		if (dog != null) {
//			return dog;
//		}
	}

	public List<Dog> getDogs() {
		return this.repo.findAll();
	}

	public Dog updateDogById(Long id, Dog newData) {
		Dog existing = this.repo.findById(id).get();

		existing.setAge(newData.getAge());
		existing.setBreed(newData.getBreed());
		existing.setColour(newData.getColour());
		existing.setSize(newData.getSize());

		return this.repo.save(existing);
	}

	public boolean deleteDogById(Long id) {
		this.repo.deleteById(id);
		boolean found = this.repo.existsById(id); // Check whether the dog has been deleted
		return !found;
	}

}
