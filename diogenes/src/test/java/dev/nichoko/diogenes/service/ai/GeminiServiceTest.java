package dev.nichoko.diogenes.service.ai;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import static org.assertj.core.api.Assertions.assertThat;

import dev.nichoko.diogenes.mock.GeminiMock;
import dev.nichoko.diogenes.mock.ImageMock;
import dev.nichoko.diogenes.mock.WebClientMock;
import dev.nichoko.diogenes.exception.ErrorReadingImageException;
import dev.nichoko.diogenes.exception.GeminiErrorException;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class GeminiServiceTest {

    @Mock
    private WebClient webClient;

    @InjectMocks
    private GeminiService geminiService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sendPromptWithImage_Success() throws Exception {
        String imagePath = "src/test/resources/sample/example.jpg";
        MockMultipartFile imageFile = ImageMock.getMockMultipartImage(imagePath);

        WebClient mockWebClient = WebClientMock.getWebClientMock(GeminiMock.getMockGeminiResponse(), HttpStatus.OK);
        ReflectionTestUtils.setField(geminiService, "webClient", mockWebClient);

        String result = geminiService.sendPromptWithImage("SOME_PROMPT", imageFile);

        assertThat(result).isEqualTo(
                "{\"item\": \"name\", \"description\": \"some description\"}");
    }

    @Test
    void sendPrompt_Success() {

        WebClient mockWebClient = WebClientMock.getWebClientMock(GeminiMock.getMockGeminiResponse(), HttpStatus.OK);
        ReflectionTestUtils.setField(geminiService, "webClient", mockWebClient);

        String result = geminiService.sendPrompt("SOME_PROMPT");

        assertThat(result).isEqualTo(
                "{\"item\": \"name\", \"description\": \"some description\"}");
    }

    @Test
    void sendPromptWithImage_GeminiError() throws Exception {
        String imagePath = "src/test/resources/sample/example.jpg";
        MockMultipartFile imageFile = ImageMock.getMockMultipartImage(imagePath);

        WebClient mockWebClient = WebClientMock.getWebClientMock(GeminiMock.getMockGeminiResponseError(),
                HttpStatus.FORBIDDEN);
        ReflectionTestUtils.setField(geminiService, "webClient", mockWebClient);

        assertThrows(GeminiErrorException.class, () -> geminiService.sendPromptWithImage("SOME_PROMPT", imageFile));
    }

    @Test
    void sendPromptWithImage_CanNotReadImage() throws Exception {
        MultipartFile mockFile = mock(MultipartFile.class);
        when(mockFile.getBytes()).thenThrow(new IOException("Simulated IO error"));

        assertThrows(ErrorReadingImageException.class, () -> {
            geminiService.sendPromptWithImage("SOME_PROMPT", mockFile);
        });
    }
}
