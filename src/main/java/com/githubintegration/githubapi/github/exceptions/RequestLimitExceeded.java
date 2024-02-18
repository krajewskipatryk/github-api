package com.githubintegration.githubapi.github.exceptions;

import org.springframework.http.HttpStatus;

public class RequestLimitExceeded extends ApiException{
    public RequestLimitExceeded() {
    }

    public RequestLimitExceeded(HttpStatus status, String errorMessage) {
        super(status, errorMessage);
    }
}
