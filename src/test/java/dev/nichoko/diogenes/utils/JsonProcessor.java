package dev.nichoko.diogenes.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class JsonProcessor {

    /**
     * Use jackson library to stringify the provided class
     * 
     * @param object
     * @return
     * @throws JsonProcessingException
     */
    public static String stringifyClass(Object object) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(object);
    }

    /**
     * Read a string as an object
     * 
     * @param string
     * @return
     * @throws JsonProcessingException
     */
    public static JsonNode readJsonString(String string) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(string);
    }
}
