package dev.nichoko.diogenes.model.ai.gemini;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonDeserialize(as = TextPart.class)
public class TextPart extends Part {
    private String text;
}