package dev.nichoko.diogenes.common;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import dev.nichoko.diogenes.model.domain.Category;
import dev.nichoko.diogenes.utils.JsonProcessor;

public class CategoryManager {
    /*
     * Sends the provided category API
     */
    public static ResultActions createCategory(MockMvc mockMvc, Category category) throws Exception {
        return mockMvc.perform(
                post("/api/v1/categories/")
                        .content(JsonProcessor.stringifyClass(category))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
    }

    /*
     * Sends the provided category to the API
     */
    public static ResultActions updateCategory(MockMvc mockMvc, Category category, int categoryId) throws Exception {
        return mockMvc.perform(
                put("/api/v1/categories/" + Integer.toString(categoryId))
                        .content(JsonProcessor.stringifyClass(category))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
    }

}
