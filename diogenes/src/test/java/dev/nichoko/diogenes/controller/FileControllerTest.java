package dev.nichoko.diogenes.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.FileInputStream;
import java.nio.file.Paths;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import dev.nichoko.diogenes.common.ItemManager;
import dev.nichoko.diogenes.mock.ItemMock;
import dev.nichoko.diogenes.model.domain.Item;
import dev.nichoko.diogenes.utils.JsonProcessor;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class FilesControllerTest {
    @Autowired
    private FilesController controller;

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
     * Verify that a non existing image can not be found
     *
     * @throws Exception
     */
    @Test
    void canNotRetrieveNotExistentImage() throws Exception {
        this.mockMvc.perform(
                get("/api/v1/files/images/wrong.jpg"))
                .andExpect(status().isNotFound());
    }

    /**
     * Can retrieve the image of an item
     *
     * @throws Exception
     */
    @Test
    void canRetrieveAnImage() throws Exception {
        Item item = ItemMock.getMockItem(1);

        String imagePath = "src/test/resources/sample/example.jpg";

        MockMultipartFile imagePart = new MockMultipartFile(
                "image",
                Paths.get(imagePath).getFileName().toString(),
                MediaType.IMAGE_JPEG_VALUE,
                new FileInputStream(imagePath));

        String jsonResponse = ItemManager.createItemWithImage(this.mockMvc, item, imagePart).andReturn().getResponse()
                .getContentAsString();
        String createdImagePath = JsonProcessor.readJsonString(jsonResponse).get("imagePath").asText();

        this.mockMvc.perform(get(createdImagePath))
                .andExpect(status().isOk());
    }

}
