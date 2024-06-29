package com.example.backend_assingment.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;

/**
 * Utility class to handle JSON serialization & deserialization
 */
@Slf4j
public class JsonUtils {

    private JsonUtils() {}

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static String getJson(Object data) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        try {
            return mapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            log.error("Json serialization exception: {}", e);
            return null;
        }
    }

    public static String getJsonWithAllTokens(Object data) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.ALWAYS);
        try {
            return mapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            log.error("Json serialization exception: {}", e);
            return null;
        }
    }

    public static <T> T getObject(String json, Class<T> type) {
        if (json == null) {
            return null;
        }
        try {
            return MAPPER.readValue(json, type);
        } catch (IOException e) {
            log.error("Json deserialization exception: {}", e);
            return null;
        }
    }

    public static <T> T getJsonObject(String json, Class<T> type) {
        if (json == null) {
            return null;
        }
        try {
            return MAPPER.readValue(json, type);
        } catch (IOException e) {
            log.error("Something Went wrong while Json deserialize");
            return null;
        }
    }
    public static <T> T getObject(Object obj, ParameterizedTypeReference<T> type) {
        if (obj == null) {
            return null;
        }
        TypeReference<T> typeRef = new CustomTypeReference(type);

        return MAPPER.convertValue(obj, typeRef);
    }

    public static <T> T getObject(String json, ParameterizedTypeReference<T> type) {
        if (json == null) {
            return null;
        }
        TypeReference<T> typeRef = new CustomTypeReference(type);
        try {
            return MAPPER.readValue(json, typeRef);
        } catch (IOException e) {
            log.error("Json deserialization exception: {}", e);
            return null;
        }
    }

    public static Map<String, Object> getMap(Object data) {
        return MAPPER.convertValue(data, new TypeReference<Map<String, Object>>() {});
    }

    public static <T> T getObject(Map<String, Object> map, Class<T> type) {
        if (map == null) {
            return null;
        }

        return MAPPER.convertValue(map, type);
    }

    public static boolean isValidJson(String jsonString) {
        try (JsonParser parser = MAPPER.getFactory().createParser(jsonString)) {
            while (parser.nextToken() != null) {
                // iterate all tokens
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}


/**
 * Custom TypeReference implementation for translation purposes
 * 
 */
class CustomTypeReference<T> extends TypeReference<Object> {
    private final Type type;

    public CustomTypeReference(ParameterizedTypeReference<T> pt) {
        this.type = pt.getType();
    }

    @Override
    public Type getType() {
        return type;
    }
}


