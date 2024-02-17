package com.githubintegration.githubapi.github.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.githubintegration.githubapi.github.exceptions.JsonMarshalException;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.Collection;

public class JsonUtils {
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static String marshalJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (IOException e) {
            throw new JsonMarshalException(HttpStatus.BAD_REQUEST, getErrorMessage(e.getMessage()));
        }
    }

    public static <T> T unmarshalJson(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new JsonMarshalException(HttpStatus.BAD_REQUEST, getErrorMessage(e.getMessage()));
        }
    }

   public static <T> T unmarshalJsonCollection(String json, Class<? extends Collection> collection, Class<?> clazz) {
       try {
           CollectionType typeReference = TypeFactory.defaultInstance().constructCollectionType(collection, clazz);
           return mapper.readValue(json, typeReference);
       } catch (IOException e) {
           throw new JsonMarshalException(HttpStatus.BAD_REQUEST, getErrorMessage(e.getMessage()));
       }
   }

   private static String getErrorMessage(String errorMessage) {
        return STR."Invalid JSON format: \{errorMessage}";
   }
}
