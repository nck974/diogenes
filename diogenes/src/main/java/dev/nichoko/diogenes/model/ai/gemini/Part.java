package dev.nichoko.diogenes.model.ai.gemini;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import dev.nichoko.diogenes.utils.deserializer.PartDeserializer;

@JsonDeserialize(using = PartDeserializer.class)
public abstract class Part {
}