package com.githubintegration.githubapi.github.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class GithubApiEmptyResultSetException extends ApiException {
    public GithubApiEmptyResultSetException() {
    }

    public GithubApiEmptyResultSetException(HttpStatus status, String errorMessage) {
        super(status, errorMessage);
    }
}
