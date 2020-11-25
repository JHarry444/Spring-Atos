package com.qa.demo.persistence.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.demo.persistence.domain.Dog;

@Repository
public interface DogRepo extends JpaRepository<Dog, Long> {

	List<Dog> findByBreed(String breed);
}
