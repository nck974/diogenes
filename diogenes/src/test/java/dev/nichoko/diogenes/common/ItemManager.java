package dev.nichoko.diogenes.common;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.io.UnsupportedEncodingException;

import dev.nichoko.diogenes.model.domain.Item;
import dev.nichoko.diogenes.utils.JsonProcessor;

public class ItemManager {

    /**
     * Create first the category and assign the id to the item
     * 
     * @param item
     * @throws UnsupportedEncodingException
     * @throws Exception
     * @throws JsonProcessingException
     */
    private static void tryCreateCategoryIfNotExists(MockMvc mockMvc, Item item)
            throws UnsupportedEncodingException, Exception, JsonProcessingException {
        try {
            String categoryString = CategoryManager.createCategory(mockMvc, item.getCategory()).andReturn()
                    .getResponse()
                    .getContentAsString();
            if (categoryString != null) {
                int categoryId = JsonProcessor
                        .readJsonString(categoryString)
                        .get("id")
                        .asInt(0);
                item.setCategoryId(categoryId);
            }
        } catch (java.lang.NullPointerException e) {

        }
    }

    /*
     * Sends the provided item to the API
     */
    public static ResultActions createItem(MockMvc mockMvc, Item item) throws Exception {

        tryCreateCategoryIfNotExists(mockMvc, item);

        return mockMvc.perform(
                post("/api/v1/item/")
                        .content(JsonProcessor.stringifyClass(item))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
    }

    /*
     * Sends the provided item to the API with the given image
     */
    public static ResultActions createItemWithImage(MockMvc mockMvc, Item item, MockMultipartFile imagePart)
            throws Exception {

        tryCreateCategoryIfNotExists(mockMvc, item);

        // Create a MockMultipartFile for the JSON content
        MockMultipartFile itemPart = new MockMultipartFile(
                "item",
                null, // Provide a filename for the JSON content
                MediaType.APPLICATION_JSON_VALUE, // Set the content type for JSON
                JsonProcessor.stringifyClass(item).getBytes());

        if (imagePart != null) {
            return mockMvc.perform(
                    MockMvcRequestBuilders.multipart("/api/v1/item/")
                            .file(itemPart)
                            .file(imagePart)
                            .contentType(MediaType.MULTIPART_FORM_DATA)
                            .accept(MediaType.APPLICATION_JSON));
        }
        return mockMvc.perform(
                MockMvcRequestBuilders.multipart("/api/v1/item/")
                        .file(itemPart)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .accept(MediaType.APPLICATION_JSON));
    }
}
