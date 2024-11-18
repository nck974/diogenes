package dev.nichoko.diogenes.service.ai;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import dev.nichoko.diogenes.exception.ErrorReadingImageException;
import dev.nichoko.diogenes.exception.GeminiErrorException;
import dev.nichoko.diogenes.model.ai.gemini.Content;
import dev.nichoko.diogenes.model.ai.gemini.GeminiRequest;
import dev.nichoko.diogenes.model.ai.gemini.GenerationConfig;
import dev.nichoko.diogenes.model.ai.gemini.InlineData;
import dev.nichoko.diogenes.model.ai.gemini.InlineDataPart;
import dev.nichoko.diogenes.model.ai.gemini.TextPart;
import dev.nichoko.diogenes.model.ai.gemini.response.GeminiResponse;
import dev.nichoko.diogenes.service.AiService;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Service
@Log4j2
public class GeminiService implements AiService {

    @Value("${ai.gemini.api-key}")
    String apiKey;

    private final WebClient webClient;
    private static String aiModel = "gemini-1.5-flash";
    private static String geminiBaseUrl = "https://generativelanguage.googleapis.com/v1beta/models";
    private GenerationConfig generationConfig = new GenerationConfig(1.0, 40, 0.95, 8192, "application/json");

    public GeminiService() {
        this.webClient = buildWebClient();
    }

    private WebClient buildWebClient() {
        return WebClient.builder()
                .baseUrl(GeminiService.geminiBaseUrl + "/" + GeminiService.aiModel + ":")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    private GeminiRequest getGeminiRequest(String prompt) {
        return new GeminiRequest(
                Collections.singletonList(
                        new Content("user",
                                Collections.singletonList(
                                        new TextPart(prompt)))),
                generationConfig);
    }

    private byte[] getImageBytes(MultipartFile imageFile) {
        try {
            return imageFile.getBytes();
        } catch (

        IOException exc) {
            throw new ErrorReadingImageException("Error reading image content");
        }
    }

    private String getImageContentType(MultipartFile imageFile) {
        return imageFile.getContentType();

    }

    private GeminiRequest getGeminiImageRequest(String prompt, MultipartFile imageFile) {

        byte[] imageBytes = getImageBytes(imageFile);
        String contentType = getImageContentType(imageFile);
        return new GeminiRequest(
                Collections.singletonList(
                        new Content("user",
                                Arrays.asList(
                                        new TextPart(prompt),
                                        new InlineDataPart(new InlineData(contentType,
                                                Base64.getEncoder().encodeToString(imageBytes)))))),
                generationConfig);
    }

    private String sendGeminiRequest(GeminiRequest request) {
        return webClient.post()
                .uri("generateContent?key=" + apiKey)
                .bodyValue(request)
                .retrieve()
                .onStatus(
                        HttpStatusCode::isError,
                        clientResponse -> clientResponse.bodyToMono(String.class)
                                .doOnNext(errorMessage -> log.error("Gemini Response: " + errorMessage))
                                .then(Mono.error(new GeminiErrorException(
                                        "Gemini error: " + clientResponse.statusCode()))))
                .bodyToMono(GeminiResponse.class)
                .block()
                .getGeneratedContent();
    }

    @Override
    public String sendPrompt(String prompt) {
        GeminiRequest request = this.getGeminiRequest(prompt);

        return sendGeminiRequest(request);
    }

    @Override
    public String sendPromptWithImage(String prompt, MultipartFile imageFile) {
        GeminiRequest request = this.getGeminiImageRequest(prompt, imageFile);

        return sendGeminiRequest(request);
    }

}
