package com.githubintegration.githubapi.github.exceptions;

import org.springframework.http.HttpStatus;

class ApiException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private HttpStatus status;
    private String errorMessage;

    public ApiException() {
    }

    public ApiException(HttpStatus status, String errorMessage) {
        this.status = status;
        this.errorMessage = errorMessage;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
