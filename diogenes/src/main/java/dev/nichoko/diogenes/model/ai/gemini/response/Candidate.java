package dev.nichoko.diogenes.model.ai.gemini.response;

import java.util.List;

import dev.nichoko.diogenes.model.ai.gemini.Content;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Candidate {
    private Content content;
    private String finishReason;
    private int index;
    private List<SafetyRating> safetyRatings;
}
