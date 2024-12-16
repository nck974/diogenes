package dev.nichoko.diogenes.model.ai.gemini;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GeminiRequest {
    private final List<Content> contents;
    private final GenerationConfig generationConfig;

}
