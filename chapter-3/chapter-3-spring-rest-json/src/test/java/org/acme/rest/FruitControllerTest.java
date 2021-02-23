package org.acme.rest;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Optional;

import org.acme.service.FruitService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(FruitController.class)
class FruitControllerTest {
	@Autowired
	MockMvc mockMvc;

	@MockBean
	FruitService fruitService;

	@Test
	public void list() throws Exception {
		Mockito.when(this.fruitService.getFruits())
			.thenReturn(List.of(new Fruit("Apple", "Winter fruit")));

		this.mockMvc.perform(get("/fruits"))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$", hasSize(1)))
			.andExpect(jsonPath("[0].name").value("Apple"))
			.andExpect(jsonPath("[0].description").value("Winter fruit"));

		Mockito.verify(this.fruitService).getFruits();
		Mockito.verifyNoMoreInteractions(this.fruitService);
	}

	@Test
	public void getFruitFound() throws Exception {
		Mockito.when(this.fruitService.getFruit(Mockito.eq("Apple")))
			.thenReturn(Optional.of(new Fruit("Apple", "Winter fruit")));

		this.mockMvc.perform(get("/fruits/Apple"))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("name").value("Apple"))
			.andExpect(jsonPath("description").value("Winter fruit"));

		Mockito.verify(this.fruitService).getFruit(Mockito.eq("Apple"));
		Mockito.verifyNoMoreInteractions(this.fruitService);
	}

	@Test
	public void getFruitNotFound() throws Exception {
		Mockito.when(this.fruitService.getFruit(Mockito.eq("pear")))
			.thenReturn(Optional.empty());

		this.mockMvc.perform(get("/fruits/pear"))
			.andExpect(status().isNotFound())
			.andExpect(content().string(""));

		Mockito.verify(this.fruitService).getFruit(Mockito.eq("pear"));
		Mockito.verifyNoMoreInteractions(this.fruitService);
	}

	@Test
	public void add() throws Exception {
		Mockito.when(this.fruitService.addFruit(Mockito.any(Fruit.class)))
			.thenReturn(List.of(new Fruit("Pear", "Refreshing fruit")));

		this.mockMvc.perform(
			post("/fruits")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"name\":\"Pear\",\"description\":\"Refreshing fruit\"}")
		)
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$", hasSize(1)))
			.andExpect(jsonPath("[0].name").value("Pear"))
			.andExpect(jsonPath("[0].description").value("Refreshing fruit"));

		Mockito.verify(this.fruitService).addFruit(Mockito.any(Fruit.class));
		Mockito.verifyNoMoreInteractions(this.fruitService);
	}

	@Test
	public void addInvalidFruit() throws Exception {
		this.mockMvc.perform(
			post("/fruits")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"description\":\"Description\"}")
		)
			.andExpect(status().isBadRequest());

		Mockito.verifyNoInteractions(this.fruitService);
	}

	@Test
	public void delete() throws Exception {
		this.mockMvc.perform(
			MockMvcRequestBuilders.delete("/fruits")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"name\":\"Apple\",\"description\":\"Apple\"}")
		)
			.andExpect(status().isNoContent());

		Mockito.verify(this.fruitService).deleteFruit(Mockito.eq("Apple"));
		Mockito.verifyNoMoreInteractions(this.fruitService);
	}

	@Test
	public void deleteInvalidFruit() throws Exception {
		this.mockMvc.perform(
			MockMvcRequestBuilders.delete("/fruits")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"description\":\"Description\"}")
		)
			.andExpect(status().isBadRequest());

		Mockito.verifyNoInteractions(this.fruitService);
	}
}
