package dev.nichoko.diogenes.model.ai.gemini.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SafetyRating {
    private String category;
    private String probability;
}
