package dev.nichoko.diogenes.mock;

import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

public class WebClientMock {
    public static WebClient getWebClientMock(String content, HttpStatus status) {
        return WebClient.builder()
                .exchangeFunction(clientRequest -> Mono.just(
                        ClientResponse.create(status)
                                .header("content-type", "application/json")
                                .body(content)
                                .build()))
                .build();
    }
}
