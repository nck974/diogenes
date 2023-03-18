package dev.nichoko.diogenes.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import dev.nichoko.diogenes.model.domain.Item;
import dev.nichoko.diogenes.service.repository.ItemRepositoryInterface;

@SpringBootTest
@AutoConfigureMockMvc
public class ItemControllerTest {
	@Autowired
	private ItemController controller;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ItemRepositoryInterface itemRepositoryInterface;

	@Test
	public void contextLoads() throws Exception {
		assertThat(controller).isNotNull();
	}

	/**
	 * Verify that when an item is not found a
	 * 
	 * @throws Exception
	 */
	@Test
	public void canGetErrorItemNotFound() throws Exception {
		this.mockMvc.perform(get("/api/v1/item/25"))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message").value("The item with id 25 could not be found"));
	}

	/**
	 * Verify that an item can be retrieved with all its parameters
	 * 
	 * @throws Exception
	 */
	@Test
	public void canSearchItemById() throws Exception {
		Item item = new Item();
		item.setId(1);
		item.setName("TestTest");
		item.setDescription("Test description of item");
		item.setNumber(1);

		// Mock database
		given(itemRepositoryInterface.findById(1L))
				.willReturn(Optional.of(item));

		this.mockMvc.perform(get("/api/v1/item/1"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(item.getId()))
				.andExpect(jsonPath("$.name").value(item.getName()))
				.andExpect(jsonPath("$.description").value(item.getDescription()))
				.andExpect(jsonPath("$.number").value(item.getNumber()));
	}

}
