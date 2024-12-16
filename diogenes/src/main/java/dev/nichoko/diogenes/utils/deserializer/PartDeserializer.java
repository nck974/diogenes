package dev.nichoko.diogenes.utils.deserializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import dev.nichoko.diogenes.model.ai.gemini.InlineDataPart;
import dev.nichoko.diogenes.model.ai.gemini.Part;
import dev.nichoko.diogenes.model.ai.gemini.TextPart;

/// See: https://stackoverflow.com/questions/32766922/jackson-deserialization-on-multiple-types
public class PartDeserializer extends JsonDeserializer<Part> {
    @Override
    public Part deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException {
        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        ObjectNode root = (ObjectNode) mapper.readTree(jp);

        if (root.has("text")) {
            return mapper.treeToValue(root, TextPart.class);
        } else {
            return mapper.treeToValue(root, InlineDataPart.class);
        }

    }
}