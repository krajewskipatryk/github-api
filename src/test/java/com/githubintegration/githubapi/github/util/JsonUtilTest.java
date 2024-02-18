package com.githubintegration.githubapi.github.util;

import com.githubintegration.githubapi.github.GithubJsonTestValues;
import com.githubintegration.githubapi.github.model.api.Repository;
import com.githubintegration.githubapi.github.model.exception.ApiError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class JsonUtilTest {
    @Test
    void unmarshalJsonCollection_passingJsonArray_shouldReturnListOfRepositories() {
        List<Repository> repositories = JsonUtil.unmarshalJsonCollection(GithubJsonTestValues.REPOSITORIES_JSON, List.class, Repository.class);

        assertFalse(repositories.isEmpty());
    }

    @Test
    void marshalJson_passingObject_shouldReturnJson() {
        ApiError apiError = new ApiError(HttpStatus.OK.toString(), "test message");
        String jsonResult = "{\"status\":\"200 OK\",\"message\":\"test message\"}";

        String json = JsonUtil.marshalJson(apiError);

        assertAll(
                () -> assertNotNull(json),
                () -> assertEquals(jsonResult, json)
        );
    }

    @Test
    void unmarshalJson_passingJson_shouldReturnObject() {
        ApiError apiErrorExpectedResult = new ApiError(HttpStatus.OK.toString(), "test message");
        String json = "{\"status\":\"200 OK\",\"message\":\"test message\"}";

        ApiError apiError = JsonUtil.unmarshalJson(json, ApiError.class);

        assertAll(
                () -> assertNotNull(apiError),
                () -> assertEquals(apiErrorExpectedResult.message(), apiError.message()),
                () -> assertEquals(apiErrorExpectedResult.status(), apiError.status())
        );
    }
}


