package com.githubintegration.githubapi.github.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class JsonMarshalException extends ApiException {
    public JsonMarshalException() {
    }

    public JsonMarshalException(HttpStatus status, String errorMessage) {
        super(status, errorMessage);
    }
}
