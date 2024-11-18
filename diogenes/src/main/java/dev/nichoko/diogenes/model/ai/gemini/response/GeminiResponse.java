package dev.nichoko.diogenes.model.ai.gemini.response;

import java.util.List;

import javax.management.RuntimeErrorException;

import dev.nichoko.diogenes.model.ai.gemini.Part;
import dev.nichoko.diogenes.model.ai.gemini.TextPart;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GeminiResponse {
    private List<Candidate> candidates;
    private UsageMetadata usageMetadata;
    private String modelVersion;

    public String getGeneratedContent() {
        if (candidates != null &&
                !candidates.isEmpty() &&
                candidates.get(0).getContent() != null &&
                candidates.get(0).getContent().getParts() != null &&
                !candidates.get(0).getContent().getParts().isEmpty()) {

            Part firstPart = candidates.get(0).getContent().getParts().get(0);

            if (firstPart.getClass() == TextPart.class) {
                return ((TextPart) firstPart).getText();
            }

            throw new RuntimeErrorException(null);
        }
        return "";
    }
}
