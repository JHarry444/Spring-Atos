package com.qa.demo.persistence.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity // req
public class Dog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String breed;

	private int age;

	private String colour;

	private String size;

	// REQUIRED
	public Dog() {
		super();
	}

	public Dog(Long id, String breed, int age, String colour, String size) {
		super();
		this.id = id;
		this.breed = breed;
		this.age = age;
		this.colour = colour;
		this.size = size;
	}

	@Override
	public String toString() {
		return "Dog [id=" + id + ", breed=" + breed + ", age=" + age + ", colour=" + colour + ", size=" + size + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBreed() {
		return breed;
	}

	public void setBreed(String breed) {
		this.breed = breed;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

}
