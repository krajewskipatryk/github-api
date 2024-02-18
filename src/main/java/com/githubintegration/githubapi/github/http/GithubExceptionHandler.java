package com.githubintegration.githubapi.github.http;

import com.githubintegration.githubapi.github.exceptions.GithubApiEmptyResultSetException;
import com.githubintegration.githubapi.github.exceptions.GithubClientException;
import com.githubintegration.githubapi.github.exceptions.JsonMarshalException;
import com.githubintegration.githubapi.github.exceptions.RequestLimitExceeded;
import com.githubintegration.githubapi.github.model.exception.ApiError;
import com.githubintegration.githubapi.github.util.JsonUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = GithubController.class)
class GithubExceptionHandler {
    @ExceptionHandler(value = GithubApiEmptyResultSetException.class)
    public ResponseEntity<String> handleException(GithubApiEmptyResultSetException exception) {
        return buildResponseEntity(exception.getStatus(), exception.getErrorMessage());
    }

    @ExceptionHandler(value = JsonMarshalException.class)
    public ResponseEntity<String> handleException(JsonMarshalException exception) {
        return buildResponseEntity(exception.getStatus(), exception.getErrorMessage());
    }

    @ExceptionHandler(value = GithubClientException.class)
    public ResponseEntity<String> handleException(GithubClientException exception) {
        return buildResponseEntity(exception.getStatus(), exception.getErrorMessage());
    }

    @ExceptionHandler(value = RequestLimitExceeded.class)
    public ResponseEntity<String> handleException(RequestLimitExceeded exception) {
        return buildResponseEntity(exception.getStatus(), exception.getErrorMessage());
    }

    private ResponseEntity<String> buildResponseEntity(HttpStatus httpStatus, String errorMessage) {
        return ResponseEntity
                .status(httpStatus)
                .body(JsonUtil.marshalJson(new ApiError(httpStatus.toString(), errorMessage)));
    }
}
