package com.githubintegration.githubapi.github.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class GithubClientException extends ApiException {
    public GithubClientException() {
    }

    public GithubClientException(HttpStatus status, String errorMessage) {
        super(status, errorMessage);
    }
}
