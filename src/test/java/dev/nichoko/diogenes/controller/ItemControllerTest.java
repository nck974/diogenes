package dev.nichoko.diogenes.controller;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.BDDMockito.given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import dev.nichoko.diogenes.model.domain.Item;
import dev.nichoko.diogenes.service.repository.ItemRepository;

@SpringBootTest
@AutoConfigureMockMvc
class ItemControllerTest {
	@Autowired
	private ItemController controller;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ItemRepository itemRepository;

	@Test
	void contextLoads() throws Exception {
		assertThat(controller).isNotNull();
	}

	/**
	 * Verify that when an item is not found a
	 *
	 * @throws Exception
	 */
	@Test
	void canGetErrorItemNotFound() throws Exception {
		this.mockMvc.perform(get("/api/v1/item/25"))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message").value("The following id could not be found: 25"));
	}

	/**
	 * Verify that an item can be retrieved with all its parameters
	 *
	 * @throws Exception
	 */
	@Test
	void canSearchItemById() throws Exception {
		Item item = new Item();
		item.setId(1);
		item.setName("TestTest");
		item.setDescription("Test description of item");
		item.setNumber(1);

		// Mock database
		given(itemRepository.findById(1L))
				.willReturn(Optional.of(item));

		this.mockMvc.perform(get("/api/v1/item/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(item.getId()))
				.andExpect(jsonPath("$.name").value(item.getName()))
				.andExpect(jsonPath("$.description").value(item.getDescription()))
				.andExpect(jsonPath("$.number").value(item.getNumber()));
	}

	/**
	 * Verify that the list of items can be returned
	 *
	 * @throws Exception
	 */
	@Test
	void canSearchAllItems() throws Exception {
		Item item = new Item(1, "TesTest", "Description", 3);
		Item item2 = new Item(2, "TesTest2", "Description", 2);

		// Mock database
		given(itemRepository.findAll())
				.willReturn(List.of(item, item2));

		this.mockMvc.perform(get("/api/v1/item/"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(2))
				.andExpect(jsonPath("$[0].name").value(item.getName()))
				.andExpect(jsonPath("$[1].name").value(item2.getName()));
	}

	/**
	 * Verify that a new item can be created
	 *
	 * @throws Exception
	 */
	@Test
	void canCreateNewItem() throws Exception {
		Item item = new Item(1, "TesTest", "Description", 3);
		// Mock database
		given(itemRepository.save(Mockito.any(Item.class)))
				.willReturn(item);

		// Create a JSON object for the item
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", item.getName());
		jsonObject.put("description", item.getDescription());
		jsonObject.put("number", item.getNumber());

		this.mockMvc.perform(
				post("/api/v1/item/")
						.content(jsonObject.toString())
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(item.getId()))
				.andExpect(jsonPath("$.name").value(item.getName()))
				.andExpect(jsonPath("$.description").value(item.getDescription()))
				.andExpect(jsonPath("$.number").value(item.getNumber()));
	}

	/**
	 * Verify create item validation: No name
	 *
	 * @throws Exception
	 */
	@Test
	void canNotCreateNewWithoutName() throws Exception {
		Item item = new Item(1, "TesTest", "Description", 3);

		// Create a JSON object for the item
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("description", item.getDescription());
		jsonObject.put("number", item.getNumber());

		this.mockMvc.perform(
				post("/api/v1/item/")
						.content(jsonObject.toString())
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	/**
	 * Verify create item validation: Number negative
	 *
	 * @throws Exception
	 */
	@Test
	void canNotCreateNewItemNegativeNumber() throws Exception {
		Item item = new Item(1, "NameNewWithoutNumber", "Description", -3);

		// Create a JSON object for the item
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", item.getName());
		jsonObject.put("description", item.getDescription());
		jsonObject.put("number", item.getNumber());

		this.mockMvc.perform(
				post("/api/v1/item/")
						.content(jsonObject.toString())
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	/**
	 * Verify create item validation: Too long description
	 *
	 * @throws Exception
	 */
	@Test
	void canNotCreateNewItemTooLongDescription() throws Exception {

		// Create a JSON object for the item
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", "TestLongDescription");
		jsonObject.put("description", "a".repeat(2001));
		jsonObject.put("number", 1);

		this.mockMvc.perform(
				post("/api/v1/item/")
						.content(jsonObject.toString())
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	/**
	 * Verify create item validation: Name too long
	 *
	 * @throws Exception
	 */
	@Test
	void canNotCreateNewWithNameTooLong() throws Exception {

		// Create a JSON object for the item
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", "a".repeat(51));
		jsonObject.put("description", "Too long name");

		this.mockMvc.perform(
				post("/api/v1/item/")
						.content(jsonObject.toString())
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	/**
	 * Verify that a new item can be updated
	 *
	 * @throws Exception
	 */
	@Test
	void canUpdateItem() throws Exception {
		Item item = new Item(1, "TesTest", "Description", 3);
		Item updatedItem = new Item(1, "NameUpdated", "Updated", 25);

		// Mock database
		given(itemRepository.findById(1L))
				.willReturn(Optional.of(item));
		given(itemRepository.save(Mockito.any(Item.class)))
				.willReturn(updatedItem);

		// Create a JSON object for the item
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", updatedItem.getName());
		jsonObject.put("description", updatedItem.getDescription());
		jsonObject.put("number", updatedItem.getNumber());

		this.mockMvc.perform(
				put("/api/v1/item/" + Integer.toString(item.getId()))
						.content(jsonObject.toString())
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(updatedItem.getId()))
				.andExpect(jsonPath("$.name").value(updatedItem.getName()))
				.andExpect(jsonPath("$.description").value(updatedItem.getDescription()))
				.andExpect(jsonPath("$.number").value(updatedItem.getNumber()));
	}

	/**
	 * Verify that a non existing item can not be updated
	 *
	 * @throws Exception
	 */
	@Test
	void canNotUpdateNotExistingItem() throws Exception {
		Item item = new Item(1, "TesTest", "Description", 3);
		Item updatedItem = new Item(1, "NameUpdated", "Updated", 25);

		// Mock database
		given(itemRepository.findById(1L))
				.willReturn(Optional.empty());

		// Create a JSON object for the item
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", updatedItem.getName());
		jsonObject.put("description", updatedItem.getDescription());
		jsonObject.put("number", updatedItem.getNumber());

		this.mockMvc.perform(
				put("/api/v1/item/" + Integer.toString(item.getId()))
						.content(jsonObject.toString())
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	/**
	 * Verify that an item can be deleted
	 *
	 * @throws Exception
	 */
	@Test
	void canDeleteItem() throws Exception {
		Item item = new Item(1, "TesTest", "Description", 3);

		// Mock database
		given(itemRepository.findById(1L))
				.willReturn(Optional.of(item));

		this.mockMvc.perform(
				delete("/api/v1/item/" + Integer.toString(item.getId()))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
	}

	/**
	 * Verify that a non existing item can not be deleted
	 *
	 * @throws Exception
	 */
	@Test
	void canNotDeleteNonExistingItem() throws Exception {
		Item item = new Item(1, "TesTest", "Description", 3);

		// Mock database
		given(itemRepository.findById(1L))
				.willReturn(Optional.empty());

		this.mockMvc.perform(
				delete("/api/v1/item/" + Integer.toString(item.getId()))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

}
