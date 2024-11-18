package dev.nichoko.diogenes.model.ai.gemini;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GenerationConfig {
    private final double temperature;
    private final int topK;
    private final double topP;
    private final int maxOutputTokens;
    private final String responseMimeType;
}
