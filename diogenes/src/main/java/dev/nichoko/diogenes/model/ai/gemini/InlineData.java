package dev.nichoko.diogenes.model.ai.gemini;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InlineData {
    @JsonProperty("mime_type")
    private String mimeType;
    private String data;

}
