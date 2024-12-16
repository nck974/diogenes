package dev.nichoko.diogenes.model.ai.gemini;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Content {
    private String role;
    private List<Part> parts;
}
