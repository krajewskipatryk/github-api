package com.githubintegration.githubapi.github.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.githubintegration.githubapi.github.exceptions.JsonMarshalException;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.StringReader;
import java.util.Collection;

public class JsonMarshaller {
    private static final ObjectMapper MAPPER;
    private static final ObjectMapper DESERIALIZE_MAPPER;

    static {
        MAPPER = new ObjectMapper();
        MAPPER.registerModule(new JavaTimeModule());
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        MAPPER.configure(SerializationFeature.WRAP_ROOT_VALUE, true);
        MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

        DESERIALIZE_MAPPER = new ObjectMapper();
        DESERIALIZE_MAPPER.registerModule(new JavaTimeModule());
        DESERIALIZE_MAPPER.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
        DESERIALIZE_MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        DESERIALIZE_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static String marshalJson(Object obj) {
        try {
            return MAPPER.writeValueAsString(obj);
        } catch (IOException e) {
            throw new JsonMarshalException(HttpStatus.BAD_REQUEST, getErrorMessage(e.getMessage()));
        }
    }

    public static <T> T unmarshalJson(String json, Class<T> clazz) {
        try {
            return DESERIALIZE_MAPPER.readValue(new StringReader(json), clazz);
        } catch (IOException e) {
            throw new JsonMarshalException(HttpStatus.BAD_REQUEST, getErrorMessage(e.getMessage()));
        }
    }

   public static <T> T unmarshalJsonCollection(String json, Class<? extends Collection> collection, Class<?> clazz) {
       try {
           CollectionType typeReference = TypeFactory.defaultInstance().constructCollectionType(collection, clazz);
           return DESERIALIZE_MAPPER.readValue(new StringReader(json), typeReference);
       } catch (IOException e) {
           throw new JsonMarshalException(HttpStatus.BAD_REQUEST, getErrorMessage(e.getMessage()));
       }
   }

   private static String getErrorMessage(String errorMessage) {
        return STR."Invalid JSON format: \{errorMessage}";
   }
}
