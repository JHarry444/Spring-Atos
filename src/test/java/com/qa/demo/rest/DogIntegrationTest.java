package com.qa.demo.rest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.demo.persistence.domain.Dog;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT) // avoids port confilcts (two things can't use the same
																// port)
@AutoConfigureMockMvc
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

		Dog expectedDog = new Dog(1L, newDog.getBreed(), newDog.getAge(), newDog.getColour(), newDog.getSize());
		String expectedDogAsJSON = this.mapper.writeValueAsString(expectedDog);
		ResultMatcher checkBody = MockMvcResultMatchers.content().json(expectedDogAsJSON);

		this.mockMVC.perform(request).andExpect(checkStatus).andExpect(checkBody);
	}

}
