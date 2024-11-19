package dev.nichoko.diogenes.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import dev.nichoko.diogenes.mock.ImageMock;
import dev.nichoko.diogenes.mock.RecognizedItemMock;
import dev.nichoko.diogenes.model.RecognizedItem;
import dev.nichoko.diogenes.service.ItemRecognitionService;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class AiControllerTest {

    @Autowired
    private AiController controller;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemRecognitionService itemRecognitionService;

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
     * Can recognize an image
     *
     * @throws Exception
     */
    @Test
    void recognizeImage_canRecognizedItem() throws Exception {
        String imagePath = "src/test/resources/sample/example.jpg";

        MockMultipartFile imageFile = ImageMock.getMockMultipartImage(imagePath);

        RecognizedItem expectedResponse = RecognizedItemMock.getRecognizedItemMock();

        when(itemRecognitionService.getItemInImage(any(MultipartFile.class)))
                .thenReturn(expectedResponse);

        mockMvc.perform(multipart("/api/v1/ai/recognize-item")
                .file(imageFile)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.item").value(expectedResponse.getItem()))
                .andExpect(jsonPath("$.description").value(expectedResponse.getDescription()));

        verify(itemRecognitionService, times(1)).getItemInImage(any(MultipartFile.class));
    }

}
