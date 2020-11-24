package com.qa.demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.qa.demo.persistence.domain.Dog;
import com.qa.demo.persistence.repo.DogRepo;
import com.qa.demo.services.DogService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class DogServiceUnitTest {

	@Autowired
	private DogService service;

	@MockBean // creates a 'mock' version of the DogRepo
	private DogRepo repo;

	@Test
	void testCreate() {
		// GIVEN
		Dog newDog = new Dog(null, "Boxer", 6, "Brindle", "M");
		Dog saved = new Dog(1L, "Boxer", 6, "Brindle", "M");

		// WHEN
		Mockito.when(this.repo.save(newDog)).thenReturn(saved);

		// THEN
		assertThat(this.service.createDog(newDog)).isEqualTo(saved);

		Mockito.verify(this.repo, Mockito.times(1)).save(newDog);
	}

	@Test
	void testUpdate() {
		// GIVEN
		Long id = 1L;
		Dog newData = new Dog(null, "Boxer", 6, "Brindle", "M");
		Dog existingDog = new Dog(id, "Doberman", 8, "Blue", "L");
		Dog updatedDog = new Dog(id, newData.getBreed(), newData.getAge(), newData.getColour(), newData.getSize());

		// WHEN
		Mockito.when(this.repo.findById(id)).thenReturn(Optional.of(existingDog));
		Mockito.when(this.repo.save(updatedDog)).thenReturn(updatedDog);

		// THEN
		assertThat(this.service.updateDogById(id, newData)).isEqualTo(updatedDog);

		Mockito.verify(this.repo, Mockito.times(1)).findById(id);
		Mockito.verify(this.repo, Mockito.times(1)).save(updatedDog);
	}

	@Test
	void testDeleteDogById() {
		// GIVEN
		Long id = 1L;

		// WHEN
		when(this.repo.existsById(id)).thenReturn(false);

		// THEN
		assertThat(this.service.deleteDogById(id)).isEqualTo(true);

		verify(this.repo, times(1)).existsById(id);
	}

}
