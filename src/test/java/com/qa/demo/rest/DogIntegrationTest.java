package com.qa.demo.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.demo.persistence.domain.Dog;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT) // avoids port confilcts (two things can't use the same
																// port)
@AutoConfigureMockMvc
// reads in sql files from src/main/resources and executes them BEFORE each test
@Sql(scripts = { "classpath:dog-schema.sql",
		"classpath:dog-data.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
public class DogIntegrationTest {

	@Autowired
	private MockMvc mockMVC;

	@Autowired
	private ObjectMapper mapper;

	@Test
	void testCreateDog() throws Exception {
		Dog newDog = new Dog(null, "Boxer", 8, "Brown", "L");
		String newDogAsJSON = this.mapper.writeValueAsString(newDog);

		RequestBuilder request = MockMvcRequestBuilders.post("/create").content(newDogAsJSON)
				.contentType(MediaType.APPLICATION_JSON);

		ResultMatcher checkStatus = status().is(201);

		Dog expectedDog = new Dog(2L, newDog.getBreed(), newDog.getAge(), newDog.getColour(), newDog.getSize());
		String expectedDogAsJSON = this.mapper.writeValueAsString(expectedDog);
		ResultMatcher checkBody = MockMvcResultMatchers.content().json(expectedDogAsJSON);

		this.mockMVC.perform(request).andExpect(checkStatus).andExpect(checkBody);
	}

	@Test
	void testCreateDogWithAssert() throws Exception {
		Dog newDog = new Dog(null, "Boxer", 8, "Brown", "L");
		String newDogAsJSON = this.mapper.writeValueAsString(newDog);

		RequestBuilder request = MockMvcRequestBuilders.post("/create").content(newDogAsJSON)
				.contentType(MediaType.APPLICATION_JSON);

		ResultMatcher checkStatus = status().is(201);

		Dog expectedDog = new Dog(2L, newDog.getBreed(), newDog.getAge(), newDog.getColour(), newDog.getSize());

		// checks the status and then returns the whole result of the request
		MvcResult result = this.mockMVC.perform(request).andExpect(checkStatus).andReturn();

		// Extracts response body as a String (will be a JSON String with this app)
		String responseBody = result.getResponse().getContentAsString();
		
		// Uses the ObjectMapper to convert the JSON String back to a Dog
		Dog actualDog = this.mapper.readValue(responseBody, Dog.class);

		// Using the AssertJ library to compare the two Dog objects - test will fail if objects don't match
		// Note: 	
		//	You WILL need an .equals() method for this.
		//	.equals() can be generated using RMB > Source > Generate hashcode() and equals()
		//	An example of the .equals() method can be found at the bottom of the Dog class
		//	More info on AssertJ available @ https://joel-costigliola.github.io/assertj/assertj-core-quick-start.html
		assertThat(actualDog).isEqualTo(expectedDog);
	}

	@Test
	void testReadAllDogs() throws Exception {
		Dog testDog = new Dog(1L, "Boxer", 6, "Brown", "L");
		List<Dog> testDogs = new ArrayList<>();
		testDogs.add(testDog);
		String testDogsAsJSON = this.mapper.writeValueAsString(testDogs);

		RequestBuilder request = get("/read");

		ResultMatcher checkStatus = status().is(200);

		ResultMatcher checkBody = content().json(testDogsAsJSON);

		this.mockMVC.perform(request).andExpect(checkStatus).andExpect(checkBody);
	}

}
