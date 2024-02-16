package com.githubintegration.githubapi.github.service;

import com.githubintegration.githubapi.github.exceptions.GithubApiEmptyResultSetException;
import com.githubintegration.githubapi.github.exceptions.GithubClientException;
import com.githubintegration.githubapi.github.model.api.Branch;
import com.githubintegration.githubapi.github.model.api.Repository;
import com.githubintegration.githubapi.github.util.JsonMarshaller;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class GithubHttpClientImpl implements GithubHttpClient {
    private final RestClient githubClient = RestClient.create();

    @Override
    public List<Repository> getRepoList(String username) {
        return githubClient
                .get()
                .uri("https://api.github.com/users/{username}/repos", username)
                .exchange(this::handleGetRepoList);
    }

    @Override
    public List<Branch> getBranchList(String username, String repository) {
        return githubClient
                .get()
                .uri("https://api.github.com/repos/{username}/{repository}/branches", username, repository)
                .exchange(this::handleGetBranchList);
    }

    @Override
    public boolean doesUserExist(String username) {
        String user = githubClient
                .get()
                .uri("https://api.github.com/users/{username}", username)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                        throw new GithubApiEmptyResultSetException(HttpStatus.NOT_FOUND,
                                STR."User with username = \{username} does not exist");
                    }
                })
                .body(String.class);

        return user != null;
    }


    private List<Branch> handleGetBranchList(HttpRequest request, ClientHttpResponse response) {
        try {
            // FIXME: Response body is not a required json, find a solution to get one here
            return getMappedList(response.getBody().toString(), Branch.class);
        } catch (IOException e) {
            throw new GithubClientException(HttpStatus.BAD_REQUEST, STR."Error occurred when parsing Branches: \{e.getMessage()}");
        }
    }

    private List<Repository> handleGetRepoList(HttpRequest request, ClientHttpResponse response) {
        try {
            // FIXME: Response body is not a required json, find a solution to get one here
            return getMappedList(response.getBody().toString(), Repository.class);
        } catch (IOException e) {
            throw new GithubClientException(HttpStatus.BAD_REQUEST, STR."Error occurred when parsing Repositories: \{e.getMessage()}");
        }

    }

    private <T> T getMappedList(String json, Class<?> clazz) {
        return JsonMarshaller.unmarshalJsonCollection(json, List.class, clazz);
    }
}
