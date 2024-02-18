package com.githubintegration.githubapi.github.exception;

import org.springframework.http.HttpStatus;

public class RequestLimitExceeded extends ApiException{
    public RequestLimitExceeded() {
    }

    public RequestLimitExceeded(HttpStatus status, String errorMessage) {
        super(status, errorMessage);
    }
}
