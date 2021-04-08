package org.acme.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Optional;

import org.acme.domain.Fruit;
import org.acme.repository.FruitRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class FruitControllerTests {
	@Autowired
	MockMvc mockMvc;

	@MockBean
	FruitRepository fruitRepository;

	@Test
	public void getAll() throws Exception {
		Mockito.when(this.fruitRepository.findAll())
			.thenReturn(List.of(new Fruit(1L, "Apple", "Hearty Fruit")));

		this.mockMvc.perform(get("/fruits"))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.size()").value(1))
			.andExpect(jsonPath("[0].id").value(1))
			.andExpect(jsonPath("[0].name").value("Apple"))
			.andExpect(jsonPath("[0].description").value("Hearty Fruit"));

		Mockito.verify(this.fruitRepository).findAll();
		Mockito.verifyNoMoreInteractions(this.fruitRepository);
	}

	@Test
	public void getFruitFound() throws Exception {
		Mockito.when(this.fruitRepository.findByName(Mockito.eq("Apple")))
			.thenReturn(Optional.of(new Fruit(1L, "Apple", "Hearty Fruit")));

		this.mockMvc.perform(get("/fruits/Apple"))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("id").value(1))
			.andExpect(jsonPath("name").value("Apple"))
			.andExpect(jsonPath("description").value("Hearty Fruit"));

		Mockito.verify(this.fruitRepository).findByName(Mockito.eq("Apple"));
		Mockito.verifyNoMoreInteractions(this.fruitRepository);
	}

	@Test
	public void getFruitNotFound() throws Exception {
		Mockito.when(this.fruitRepository.findByName(Mockito.eq("Apple")))
			.thenReturn(Optional.empty());

		this.mockMvc.perform(get("/fruits/Apple"))
			.andExpect(status().isNotFound());

		Mockito.verify(this.fruitRepository).findByName(Mockito.eq("Apple"));
		Mockito.verifyNoMoreInteractions(this.fruitRepository);
	}

	@Test
	public void addFruit() throws Exception {
		Mockito.when(this.fruitRepository.save(Mockito.any(Fruit.class)))
			.thenReturn(new Fruit(1L, "Grapefruit", "Summer fruit"));

		this.mockMvc.perform(
			post("/fruits")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"name\":\"Grapefruit\",\"description\":\"Summer fruit\"}")
		)
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("id").value(1))
			.andExpect(jsonPath("name").value("Grapefruit"))
			.andExpect(jsonPath("description").value("Summer fruit"));

		Mockito.verify(this.fruitRepository).save(Mockito.any(Fruit.class));
		Mockito.verifyNoMoreInteractions(this.fruitRepository);
	}
}
