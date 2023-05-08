package dev.nichoko.diogenes.controller;

import java.util.List;
import java.util.stream.IntStream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import dev.nichoko.diogenes.model.domain.Item;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class ItemControllerTest {
	@Autowired
	private ItemController controller;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	Flyway flyway;

	/*
	 * Clean up the database before each test
	 */
	@BeforeEach
	public void cleanUp() {
		flyway.clean();
		flyway.migrate();
	}

	/*
	 * Use jackson library to stringify the provided class
	 */
	private static String stringifyClass(Object object) throws JsonProcessingException {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		return ow.writeValueAsString(object);
	}

	/*
	 * Return a mock of an item
	 */
	private static Item getMockItem(Integer number) {
		return new Item(number, "TestName" + number.toString(), "Description" + number.toString(), number);
	}

	/*
	 * Sends the provided item to the API
	 */
	private ResultActions createItem(Item item) throws Exception {
		return this.mockMvc.perform(
				post("/api/v1/item/")
						.content(stringifyClass(item))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON));
	}

	/*
	 * Test the app can load
	 */
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
		Item item = getMockItem(1);
		createItem(item);

		this.mockMvc.perform(get("/api/v1/item/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(item.getId()))
				.andExpect(jsonPath("$.name").value(item.getName()))
				.andExpect(jsonPath("$.description").value(item.getDescription()))
				.andExpect(jsonPath("$.number").value(item.getNumber()));
	}

	/**
	 * Can create a new item
	 *
	 * @throws Exception
	 */
	@Test
	void canCreateNewItem() throws Exception {
		Item item = getMockItem(1);

		createItem(item)
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").isNumber())
				.andExpect(jsonPath("$.name").value(item.getName()))
				.andExpect(jsonPath("$.description").value(item.getDescription()))
				.andExpect(jsonPath("$.number").value(item.getNumber()));
	}

	/**
	 * Can create a new item
	 *
	 * @throws Exception
	 */
	@Test
	void canCreateItemWithoutDescription() throws Exception {
		Item item = getMockItem(1);
		item.setDescription(null);

		createItem(item)
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").isNumber())
				.andExpect(jsonPath("$.name").value(item.getName()))
				.andExpect(jsonPath("$.description").value(item.getDescription()))
				.andExpect(jsonPath("$.number").value(item.getNumber()));
	}

	/**
	 * Can filter items
	 *
	 * @throws Exception
	 */
	@Test
	void canGetAllItems() throws Exception {
		List<Item> items = List.of(getMockItem(2), getMockItem(3), getMockItem(4));
		for (Item item : items) {
			createItem(item);
		}
		this.mockMvc.perform(get("/api/v1/item/"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content.length()").value(items.size()))
				.andExpect(jsonPath("$.content[0].name").value(items.get(0).getName()))
				.andExpect(jsonPath("$.content[1].name").value(items.get(1).getName()))
				.andExpect(jsonPath("$.content[2].name").value(items.get(2).getName()));
	}

	/**
	 * Verify create item validation: No name
	 *
	 * @throws Exception
	 */
	@Test
	void canNotCreateNewWithoutName() throws Exception {
		Item item = getMockItem(1);
		item.setName(null);
		item.setDescription(null);

		createItem(item)
				.andExpect(status().isBadRequest());
	}

	/**
	 * Verify create item validation: Empty name
	 *
	 * @throws Exception
	 */
	@Test
	void canNotCreateNewEmptyName() throws Exception {
		Item item = getMockItem(1);
		item.setName("");
		item.setDescription("");

		createItem(item)
				.andExpect(status().isBadRequest());
	}

	/**
	 * Verify create item validation: Number negative
	 *
	 * @throws Exception
	 */
	@Test
	void canNotCreateNewItemNegativeNumber() throws Exception {
		Item item = getMockItem(1);
		item.setNumber(-3);

		createItem(item)
				.andExpect(status().isBadRequest());
	}

	/**
	 * Verify create item validation: Too long description
	 *
	 * @throws Exception
	 */
	@Test
	void canNotCreateNewItemTooLongDescription() throws Exception {
		Item item = getMockItem(1);
		item.setDescription("a".repeat(2001));

		createItem(item)
				.andExpect(status().isBadRequest());
	}

	/**
	 * Verify create item validation: Name too long
	 *
	 * @throws Exception
	 */
	@Test
	void canNotCreateNewWithNameTooLong() throws Exception {
		Item item = getMockItem(1);
		item.setName("a".repeat(51));

		createItem(item)
				.andExpect(status().isBadRequest());
	}

	/**
	 * Verify that a new item can be updated
	 *
	 * @throws Exception
	 */
	@Test
	void canUpdateItem() throws Exception {
		Item item = getMockItem(1);
		Item updatedItem = getMockItem(2);
		updatedItem.setId(item.getId());
		createItem(item);

		this.mockMvc.perform(
				put("/api/v1/item/" + Integer.toString(item.getId()))
						.content(stringifyClass(updatedItem))
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
		Item item = getMockItem(1);
		this.mockMvc.perform(
				put("/api/v1/item/" + Integer.toString(item.getId()))
						.content(stringifyClass(item))
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
		Item item = getMockItem(1);
		createItem(item);

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
		Item item = getMockItem(1);

		this.mockMvc.perform(
				delete("/api/v1/item/" + Integer.toString(item.getId()))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	/**
	 * Can filter items
	 *
	 * @throws Exception
	 */
	@ParameterizedTest()
	@ValueSource(strings = {
			"name",
			"number",
			"description"
	})
	void canFilterByTheAvailableParameters(String filterName) throws Exception {
		IntStream.range(0, 10).forEachOrdered(n -> {
			try {
				createItem(getMockItem(n));
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException();
			}
		});
		String filterParameter = "?" + filterName + "=5";
		this.mockMvc.perform(get("/api/v1/item/" + filterParameter))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content.length()").value(1));
	}

	/**
	 * Can filter items by two parameters
	 *
	 * @throws Exception
	 */
	@Test
	void canFilterByTwoParameters() throws Exception {
		IntStream.range(0, 10).forEachOrdered(n -> {
			try {
				createItem(getMockItem(n));
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException();
			}
		});
		String filterParameter = "?name=5&description=5";
		this.mockMvc.perform(get("/api/v1/item/" + filterParameter))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content.length()").value(1));
	}

	/**
	 * Can sort the items in reverse order
	 *
	 * @throws Exception
	 */
	@Test
	void canSortItems() throws Exception {
		List<Item> items = List.of(getMockItem(1), getMockItem(2), getMockItem(3));
		for (Item item : items) {
			createItem(item);
		}
		String sortParameter = "?sortDirection=DESC";
		this.mockMvc.perform(get("/api/v1/item/" + sortParameter))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content.length()").value(items.size()))
				.andExpect(jsonPath("$.content[0].name").value(items.get(2).getName()))
				.andExpect(jsonPath("$.content[1].name").value(items.get(1).getName()))
				.andExpect(jsonPath("$.content[2].name").value(items.get(0).getName()));
	}

}
