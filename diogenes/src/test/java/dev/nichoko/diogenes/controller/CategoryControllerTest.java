package dev.nichoko.diogenes.controller;

import java.util.List;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import dev.nichoko.diogenes.model.domain.Category;
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
     * Return a mock of an category
     */
    private static Category getMockCategory(Integer number) {
        return new Category(
                number,
                "TestName" + number.toString(),
                "Description" + number.toString(),
                "AB02" + number.toString());
    }

    /*
     * Sends the provided category to the API
     */
    private ResultActions createCategory(Category category) throws Exception {
        return this.mockMvc.perform(
                post("/api/v1/categories/")
                        .content(JsonProcessor.stringifyClass(category))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
    }

    /*
     * Sends the provided category to the API
     */
    private ResultActions updateCategory(Category category, int categoryId) throws Exception {
        return this.mockMvc.perform(
                put("/api/v1/categories/" + Integer.toString(categoryId))
                        .content(JsonProcessor.stringifyClass(category))
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
        Category category = getMockCategory(1);
        createCategory(category);

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
        Category category = getMockCategory(1);

        createCategory(category)
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
        Category category = getMockCategory(1);

        createCategory(category);
        createCategory(category)
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
        Category category = getMockCategory(1);
        Category categoryUpdate = getMockCategory(2);

        createCategory(category);
        createCategory(categoryUpdate);
        categoryUpdate.setName(category.getName());
        updateCategory(categoryUpdate, categoryUpdate.getId())
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
        Category category = getMockCategory(1);
        category.setDescription(null);

        createCategory(category)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value(category.getName()))
                .andExpect(jsonPath("$.description").value(category.getDescription()))
                .andExpect(jsonPath("$.color").value(category.getColor()));
    }

    /**
     * Can filter categories
     *
     * @throws Exception
     */
    @Test
    void canGetAllCategories() throws Exception {
        List<Category> categories = List.of(getMockCategory(2), getMockCategory(3), getMockCategory(4));
        for (Category category : categories) {
            createCategory(category);
        }
        this.mockMvc.perform(get("/api/v1/categories/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(categories.size()))
                .andExpect(jsonPath("$[0].name").value(categories.get(0).getName()))
                .andExpect(jsonPath("$[1].name").value(categories.get(1).getName()))
                .andExpect(jsonPath("$[2].name").value(categories.get(2).getName()));
    }

    /**
     * Verify create category validation: No name
     *
     * @throws Exception
     */
    @Test
    void canNotCreateNewCategoryWithoutName() throws Exception {
        Category category = getMockCategory(1);
        category.setName(null);
        category.setDescription(null);

        createCategory(category)
                .andExpect(status().isBadRequest());
    }

    /**
     * Verify create category validation: Empty name
     *
     * @throws Exception
     */
    @Test
    void canNotCreateNewEmptyName() throws Exception {
        Category category = getMockCategory(1);
        category.setName("");
        category.setDescription("");

        createCategory(category)
                .andExpect(status().isBadRequest());
    }

    /**
     * Verify create category validation: Too long description
     *
     * @throws Exception
     */
    @Test
    void canNotCreateNewCategoryTooLongDescription() throws Exception {
        Category category = getMockCategory(1);
        category.setDescription("a".repeat(2001));

        createCategory(category)
                .andExpect(status().isBadRequest());
    }

    /**
     * Verify create category validation: Name too long
     *
     * @throws Exception
     */
    @Test
    void canNotCreateNewWithNameTooLong() throws Exception {
        Category category = getMockCategory(1);
        category.setName("a".repeat(51));

        createCategory(category)
                .andExpect(status().isBadRequest());
    }

    /**
     * Verify that a new category can be updated
     *
     * @throws Exception
     */
    @Test
    void canUpdateCategory() throws Exception {
        Category category = getMockCategory(1);
        Category updatedCategory = getMockCategory(2);
        updatedCategory.setId(category.getId());
        createCategory(category);

        updateCategory(updatedCategory, category.getId())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedCategory.getId()))
                .andExpect(jsonPath("$.name").value(updatedCategory.getName()))
                .andExpect(jsonPath("$.description").value(updatedCategory.getDescription()))
                .andExpect(jsonPath("$.color").value(updatedCategory.getColor()));
    }

    /**
     * Verify that a category can be updated and there are no conflicts with the name.
     * 
     * This is added because of the validateName(...) method in item
     *
     * @throws Exception
     */
    @Test
    void canUpdateCategorySameName() throws Exception {
        Category category = getMockCategory(1);
        Category updatedCategory = getMockCategory(2);
        updatedCategory.setId(category.getId());
        updatedCategory.setName(category.getName());
        createCategory(category);

        updateCategory(updatedCategory, category.getId())
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
        Category category = getMockCategory(1);
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
        Category category = getMockCategory(1);
        createCategory(category);

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
        Category category = getMockCategory(1);

        this.mockMvc.perform(
                delete("/api/v1/categories/" + Integer.toString(category.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
