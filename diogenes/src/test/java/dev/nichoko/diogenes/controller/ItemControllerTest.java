package dev.nichoko.diogenes.controller;

import java.io.FileInputStream;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.flywaydb.core.Flyway;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import dev.nichoko.diogenes.common.ItemManager;
import dev.nichoko.diogenes.mock.ImageMock;
import dev.nichoko.diogenes.mock.ItemMock;
import dev.nichoko.diogenes.model.domain.Item;
import dev.nichoko.diogenes.utils.JsonProcessor;

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
        Item item = ItemMock.getMockItem(1);
        ItemManager.createItem(this.mockMvc, item);

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
        Item item = ItemMock.getMockItem(1);

        ItemManager.createItem(this.mockMvc, item)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value(item.getName()))
                .andExpect(jsonPath("$.description").value(item.getDescription()))
                .andExpect(jsonPath("$.number").value(item.getNumber()));
    }

    /**
     * Can create a new item with image
     *
     * @throws Exception
     */
    @Test
    void canCreateNewItemWithImage() throws Exception {
        Item item = ItemMock.getMockItem(1);

        String imagePath = "src/test/resources/sample/example.jpg";

        MockMultipartFile imagePart = ImageMock.getMockMultipartImage(imagePath);

        ItemManager.createItemWithImage(this.mockMvc, item, imagePart)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value(item.getName()))
                .andExpect(jsonPath("$.description").value(item.getDescription()))
                .andExpect(jsonPath("$.number").value(item.getNumber()))
                .andExpect(jsonPath("$.imagePath")
                        .value(Matchers
                                .matchesPattern(
                                        "^.+" + Pattern.quote(Paths.get(imagePath).getFileName().toString()) + "$")));
    }

    /**
     * Can delete the image of an item
     *
     * @throws Exception
     */
    @Test
    void canDeleteItemImage() throws Exception {
        Item item = ItemMock.getMockItem(1);

        String imagePath = "src/test/resources/sample/example.jpg";

        MockMultipartFile imagePart = ImageMock.getMockMultipartImage(imagePath);

        String createdItemJson = ItemManager.createItemWithImage(this.mockMvc, item, imagePart).andReturn()
                .getResponse().getContentAsString();
        String itemId = JsonProcessor.readJsonString(createdItemJson).get("id").asText();
        mockMvc.perform(delete("/api/v1/item/" + itemId + "/image"))
                .andExpect(status().isNoContent());
    }

    /**
     * Can update the image of an item
     *
     * @throws Exception
     */
    @Test
    void canUpdateItemImage() throws Exception {
        Item item = ItemMock.getMockItem(1);

        String imagePath = "src/test/resources/sample/example.jpg";

        MockMultipartFile imagePart = ImageMock.getMockMultipartImage(imagePath);

        String createdItemJson = ItemManager.createItemWithImage(this.mockMvc, item, imagePart).andReturn()
                .getResponse().getContentAsString();
        String itemId = JsonProcessor.readJsonString(createdItemJson).get("id").asText();
        ItemManager.updateItemWithImage(this.mockMvc, itemId, item, imagePart)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(item.getId()))
                .andExpect(jsonPath("$.name").value(item.getName()))
                .andExpect(jsonPath("$.description").value(item.getDescription()))
                .andExpect(jsonPath("$.imagePath")
                        .value(Matchers
                                .matchesPattern(
                                        "^.+" + Pattern.quote(Paths.get(imagePath).getFileName().toString()) + "$")));

    }

    /**
     * Can update the image of an item that did not have any image
     *
     * @throws Exception
     */
    @Test
    void canUpdateItemImageOfItemWithoutImage() throws Exception {
        Item item = ItemMock.getMockItem(1);

        String imagePath = "src/test/resources/sample/example.jpg";

        MockMultipartFile imagePart = ImageMock.getMockMultipartImage(imagePath);

        String createdItemJson = ItemManager.createItemWithImage(this.mockMvc, item, null).andReturn()
                .getResponse().getContentAsString();
        String itemId = JsonProcessor.readJsonString(createdItemJson).get("id").asText();
        ItemManager.updateItemWithImage(this.mockMvc, itemId, item, imagePart)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(item.getId()))
                .andExpect(jsonPath("$.name").value(item.getName()))
                .andExpect(jsonPath("$.description").value(item.getDescription()))
                .andExpect(jsonPath("$.imagePath")
                        .value(Matchers
                                .matchesPattern(
                                        "^.+" + Pattern.quote(Paths.get(imagePath).getFileName().toString()) + "$")));

    }

    /**
     * Can not delete the image of an item without images
     *
     * @throws Exception
     */
    @Test
    void canNotDeleteItemImageOfItemWithoutImages() throws Exception {
        Item item = ItemMock.getMockItem(1);

        String createdItemJson = ItemManager.createItem(this.mockMvc, item).andReturn()
                .getResponse().getContentAsString();
        String itemId = JsonProcessor.readJsonString(createdItemJson).get("id").asText();
        mockMvc.perform(delete("/api/v1/item/" + itemId + "/image"))
                .andExpect(status().isNotFound());
    }

    /**
     * Can not delete the image of a non existent item
     *
     * @throws Exception
     */
    @Test
    void canNotDeleteItemImageOfNonExistentItems() throws Exception {
        mockMvc.perform(delete("/api/v1/item/33/image"))
                .andExpect(status().isNotFound());
    }

    /**
     * Can create a new item using multipart without image
     *
     * @throws Exception
     */
    @Test
    void canCreateNewItemWithMultipartContentType() throws Exception {
        Item item = ItemMock.getMockItem(1);

        ItemManager.createItemWithImage(this.mockMvc, item, null)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value(item.getName()))
                .andExpect(jsonPath("$.description").value(item.getDescription()))
                .andExpect(jsonPath("$.number").value(item.getNumber()))
                .andExpect(jsonPath("$.imagePath")
                        .isEmpty());
    }

    /**
     * Can not create a new item with invalid image format
     *
     * @throws Exception
     */
    @Test
    void canNotCreateNewItemWithInvalidImageFormat() throws Exception {
        Item item = ItemMock.getMockItem(1);

        String imagePath = "src/test/resources/sample/example.txt";

        MockMultipartFile imagePart = new MockMultipartFile(
                "image",
                Paths.get(imagePath).getFileName().toString(),
                MediaType.TEXT_PLAIN_VALUE,
                new FileInputStream(imagePath));

        ItemManager.createItemWithImage(this.mockMvc, item, imagePart)
                .andExpect(status().isBadRequest());
    }

    /**
     * Can create a new item
     *
     * @throws Exception
     */
    @Test
    void canCreateItemWithoutDescription() throws Exception {
        Item item = ItemMock.getMockItem(1);
        item.setDescription(null);

        ItemManager.createItem(this.mockMvc, item)
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
        List<Item> items = List.of(ItemMock.getMockItem(2), ItemMock.getMockItem(3), ItemMock.getMockItem(4));
        for (Item item : items) {
            ItemManager.createItem(this.mockMvc, item);
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
        Item item = ItemMock.getMockItem(1);
        item.setName(null);
        item.setDescription(null);

        ItemManager.createItem(this.mockMvc, item)
                .andExpect(status().isBadRequest());
    }

    /**
     * Verify create item validation: Invalid category
     *
     * @throws Exception
     */
    @Test
    void canNotCreateNewWithoutValidCategoryId() throws Exception {
        Item item = ItemMock.getMockItem(1);
        item.setCategory(null);

        ItemManager.createItem(this.mockMvc, item)
                .andExpect(status().isBadRequest());
    }

    /**
     * Verify create item validation: Category 0
     *
     * @throws Exception
     */
    @Test
    void canNotCreateNewWithoutCategoryId() throws Exception {
        Item item = ItemMock.getMockItem(1);
        item.setCategoryId(0);

        this.mockMvc.perform(
                post("/api/v1/item/")
                        .content(JsonProcessor.stringifyClass(item))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    /**
     * Verify create item validation: Empty name
     *
     * @throws Exception
     */
    @Test
    void canNotCreateNewEmptyName() throws Exception {
        Item item = ItemMock.getMockItem(1);
        item.setName("");
        item.setDescription("");

        ItemManager.createItem(this.mockMvc, item)
                .andExpect(status().isBadRequest());
    }

    /**
     * Verify create item validation: Number negative
     *
     * @throws Exception
     */
    @Test
    void canNotCreateNewItemNegativeNumber() throws Exception {
        Item item = ItemMock.getMockItem(1);
        item.setNumber(-3);

        ItemManager.createItem(this.mockMvc, item)
                .andExpect(status().isBadRequest());
    }

    /**
     * Verify create item validation: Too long description
     *
     * @throws Exception
     */
    @Test
    void canNotCreateNewItemTooLongDescription() throws Exception {
        Item item = ItemMock.getMockItem(1);
        item.setDescription("a".repeat(2001));

        ItemManager.createItem(this.mockMvc, item)
                .andExpect(status().isBadRequest());
    }

    /**
     * Verify create item validation: Name too long
     *
     * @throws Exception
     */
    @Test
    void canNotCreateNewWithNameTooLong() throws Exception {
        Item item = ItemMock.getMockItem(1);
        item.setName("a".repeat(51));

        ItemManager.createItem(this.mockMvc, item)
                .andExpect(status().isBadRequest());
    }

    /**
     * Verify that a new item can be updated
     *
     * @throws Exception
     */
    @Test
    void canUpdateItem() throws Exception {
        Item item = ItemMock.getMockItem(1);
        Item updatedItem = ItemMock.getMockItem(2);
        updatedItem.setId(item.getId());
        updatedItem.setCategory(item.getCategory());
        ItemManager.createItem(this.mockMvc, item);

        ItemManager.updateItem(this.mockMvc, updatedItem)
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
        Item item = ItemMock.getMockItem(1);
        ItemManager.updateItem(this.mockMvc, item)
                .andExpect(status().isNotFound());
    }

    /**
     * Verify that an item can be deleted
     *
     * @throws Exception
     */
    @Test
    void canDeleteItem() throws Exception {
        Item item = ItemMock.getMockItem(1);
        ItemManager.createItem(this.mockMvc, item);

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
        Item item = ItemMock.getMockItem(1);

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
            "description",
            "categoryId"
    })
    void canFilterByTheAvailableParameters(String filterName) throws Exception {
        IntStream.range(0, 10).forEachOrdered(n -> {
            try {
                ItemManager.createItem(this.mockMvc, ItemMock.getMockItem(n));
            } catch (Exception e) {
                System.out.println(e.getMessage());
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
                ItemManager.createItem(this.mockMvc, ItemMock.getMockItem(n));
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
     * Can filter items when parameter is empty
     *
     * @throws Exception
     */
    @Test
    void canFilterWithEmptyParameters() throws Exception {
        IntStream.range(0, 10).forEachOrdered(n -> {
            try {
                ItemManager.createItem(this.mockMvc, ItemMock.getMockItem(n));
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
        });
        String filterParameter = "?name=&description=&number=1";
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
        List<Item> items = List.of(ItemMock.getMockItem(1), ItemMock.getMockItem(2), ItemMock.getMockItem(3));
        for (Item item : items) {
            ItemManager.createItem(this.mockMvc, item);
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
