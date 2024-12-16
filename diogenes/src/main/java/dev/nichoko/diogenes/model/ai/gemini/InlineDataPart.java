package dev.nichoko.diogenes.model.ai.gemini;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonDeserialize(as = InlineDataPart.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class InlineDataPart extends Part {
    @JsonProperty("inline_data")
    InlineData inlineData;
}