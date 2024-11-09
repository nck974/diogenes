package dev.nichoko.diogenes.controller;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import dev.nichoko.diogenes.common.CategoryManager;
import dev.nichoko.diogenes.common.ItemManager;
import dev.nichoko.diogenes.mock.CategoryMock;
import dev.nichoko.diogenes.mock.ItemMock;
import dev.nichoko.diogenes.model.domain.Category;
import dev.nichoko.diogenes.model.domain.Item;
import dev.nichoko.diogenes.utils.JsonProcessor;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class CategoryControllerTest {
    @Autowired
    private CategoryController controller;

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
    void contextLoads() {
        assertThat(controller).isNotNull();
    }

    /**
     * Verify that when an category is not found a
     *
     * @throws Exception
     */
    @Test
    void canGetErrorCategoryNotFound() throws Exception {
        this.mockMvc.perform(get("/api/v1/categories/25"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("The following id could not be found: 25"));
    }

    /**
     * Verify that an category can be retrieved with all its parameters
     *
     * @throws Exception
     */
    @Test
    void canSearchCategoryById() throws Exception {
        Category category = CategoryMock.getMockCategory(1);
        CategoryManager.createCategory(this.mockMvc, category);

        this.mockMvc.perform(get("/api/v1/categories/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(category.getId()))
                .andExpect(jsonPath("$.name").value(category.getName()))
                .andExpect(jsonPath("$.description").value(category.getDescription()))
                .andExpect(jsonPath("$.color").value(category.getColor()));
    }

    /**
     * Can create a new category
     *
     * @throws Exception
     */
    @Test
    void canCreateNewCategory() throws Exception {
        Category category = CategoryMock.getMockCategory(1);

        CategoryManager.createCategory(this.mockMvc, category)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value(category.getName()))
                .andExpect(jsonPath("$.description").value(category.getDescription()))
                .andExpect(jsonPath("$.color").value(category.getColor()));
    }

    /**
     * Can not create a new category with a duplicated name
     *
     * @throws Exception
     */
    @Test
    void canNotCreateCategoryWithTheSameName() throws Exception {
        Category category = CategoryMock.getMockCategory(1);

        CategoryManager.createCategory(this.mockMvc, category);
        CategoryManager.createCategory(this.mockMvc, category)
                .andExpect(status().isConflict())
                .andExpect(
                        jsonPath("message").value("Category with the name " + category.getName() + " already exists."));
    }

    /**
     * Can not create a update a category with a duplicated name
     *
     * @throws Exception
     */
    @Test
    void canNotUpdateCategoryWithTheSameName() throws Exception {
        Category category = CategoryMock.getMockCategory(1);
        Category categoryUpdate = CategoryMock.getMockCategory(2);

        CategoryManager.createCategory(this.mockMvc, category);
        CategoryManager.createCategory(this.mockMvc, categoryUpdate);
        categoryUpdate.setName(category.getName());
        CategoryManager.updateCategory(this.mockMvc, categoryUpdate, categoryUpdate.getId())
                .andExpect(status().isConflict())
                .andExpect(
                        jsonPath("message").value("Category with the name " + category.getName() + " already exists."));
    }

    /**
     * Can create a new category
     *
     * @throws Exception
     */
    @Test
    void canCreateCategoryWithoutDescription() throws Exception {
        Category category = CategoryMock.getMockCategory(1);
        category.setDescription(null);

        CategoryManager.createCategory(this.mockMvc, category)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value(category.getName()))
                .andExpect(jsonPath("$.description").value(category.getDescription()))
                .andExpect(jsonPath("$.color").value(category.getColor()));
    }

    /**
     * Can get all categories
     *
     * @throws Exception
     */
    @Test
    void canGetAllCategories() throws Exception {
        List<Category> categories = List.of(CategoryMock.getMockCategory(2), CategoryMock.getMockCategory(3),
                CategoryMock.getMockCategory(4));
        for (Category category : categories) {
            CategoryManager.createCategory(this.mockMvc, category);
        }
        this.mockMvc.perform(get("/api/v1/categories/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(categories.size()))
                .andExpect(jsonPath("$[0].name").value(categories.get(0).getName()))
                .andExpect(jsonPath("$[1].name").value(categories.get(1).getName()))
                .andExpect(jsonPath("$[2].name").value(categories.get(2).getName()));
    }

    /**
     * Can get the categories summary
     *
     * @throws Exception
     */
    @Test
    void canGetCategoriesSummary() throws Exception {
        List<Category> categories = List.of(CategoryMock.getMockCategory(1), CategoryMock.getMockCategory(2));
        for (Category category : categories) {
            CategoryManager.createCategory(this.mockMvc, category);
        }

        // Add two items
        List<Item> items = List.of(ItemMock.getMockItem(1), ItemMock.getMockItem(2));
        for (Item item : items) {
            item.setCategory(categories.get(0));
            ItemManager.createItem(this.mockMvc, item);
        }

        this.mockMvc.perform(get("/api/v1/categories/summary"))
                .andDo(res -> System.out.println(res))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(categories.size()))
                .andExpect(jsonPath("$[0].category.name").value(categories.get(0).getName()))
                .andExpect(jsonPath("$[0].itemsNumber").value(2))
                .andExpect(jsonPath("$[1].category.name").value(categories.get(1).getName()))
                .andExpect(jsonPath("$[1].itemsNumber").value(0));
    }

    /**
     * Verify create category validation: No name
     *
     * @throws Exception
     */
    @Test
    void canNotCreateNewCategoryWithoutName() throws Exception {
        Category category = CategoryMock.getMockCategory(1);
        category.setName(null);
        category.setDescription(null);

        CategoryManager.createCategory(this.mockMvc, category)
                .andExpect(status().isBadRequest());
    }

    /**
     * Verify create category validation: Empty name
     *
     * @throws Exception
     */
    @Test
    void canNotCreateNewEmptyName() throws Exception {
        Category category = CategoryMock.getMockCategory(1);
        category.setName("");
        category.setDescription("");

        CategoryManager.createCategory(this.mockMvc, category)
                .andExpect(status().isBadRequest());
    }

    /**
     * Verify create category validation: Too long description
     *
     * @throws Exception
     */
    @Test
    void canNotCreateNewCategoryTooLongDescription() throws Exception {
        Category category = CategoryMock.getMockCategory(1);
        category.setDescription("a".repeat(2001));

        CategoryManager.createCategory(this.mockMvc, category)
                .andExpect(status().isBadRequest());
    }

    /**
     * Verify create category validation: Name too long
     *
     * @throws Exception
     */
    @Test
    void canNotCreateNewWithNameTooLong() throws Exception {
        Category category = CategoryMock.getMockCategory(1);
        category.setName("a".repeat(51));

        CategoryManager.createCategory(this.mockMvc, category)
                .andExpect(status().isBadRequest());
    }

    /**
     * Verify that a new category can be updated
     *
     * @throws Exception
     */
    @Test
    void canUpdateCategory() throws Exception {
        Category category = CategoryMock.getMockCategory(1);
        Category updatedCategory = CategoryMock.getMockCategory(2);
        updatedCategory.setId(category.getId());
        CategoryManager.createCategory(this.mockMvc, category);

        CategoryManager.updateCategory(this.mockMvc, updatedCategory, category.getId())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedCategory.getId()))
                .andExpect(jsonPath("$.name").value(updatedCategory.getName()))
                .andExpect(jsonPath("$.description").value(updatedCategory.getDescription()))
                .andExpect(jsonPath("$.color").value(updatedCategory.getColor()));
    }

    /**
     * Verify that a category can be updated and there are no conflicts with the
     * name.
     * 
     * This is added because of the validateName(...) method in item
     *
     * @throws Exception
     */
    @Test
    void canUpdateCategorySameName() throws Exception {
        Category category = CategoryMock.getMockCategory(1);
        Category updatedCategory = CategoryMock.getMockCategory(2);
        updatedCategory.setId(category.getId());
        updatedCategory.setName(category.getName());
        CategoryManager.createCategory(this.mockMvc, category);

        CategoryManager.updateCategory(this.mockMvc, updatedCategory, category.getId())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedCategory.getId()))
                .andExpect(jsonPath("$.name").value(updatedCategory.getName()))
                .andExpect(jsonPath("$.description").value(updatedCategory.getDescription()))
                .andExpect(jsonPath("$.color").value(updatedCategory.getColor()));
    }

    /**
     * Verify that a non existing category can not be updated
     *
     * @throws Exception
     */
    @Test
    void canNotUpdateNotExistingCategory() throws Exception {
        Category category = CategoryMock.getMockCategory(1);
        this.mockMvc.perform(
                put("/api/v1/categories/" + Integer.toString(category.getId()))
                        .content(JsonProcessor.stringifyClass(category))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    /**
     * Verify that an category can be deleted
     *
     * @throws Exception
     */
    @Test
    void canDeleteCategory() throws Exception {
        Category category = CategoryMock.getMockCategory(1);
        CategoryManager.createCategory(this.mockMvc, category);

        this.mockMvc.perform(
                delete("/api/v1/categories/" + Integer.toString(category.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    /**
     * Verify that a non existing category can not be deleted
     *
     * @throws Exception
     */
    @Test
    void canNotDeleteNonExistingCategory() throws Exception {
        Category category = CategoryMock.getMockCategory(1);

        this.mockMvc.perform(
                delete("/api/v1/categories/" + Integer.toString(category.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
