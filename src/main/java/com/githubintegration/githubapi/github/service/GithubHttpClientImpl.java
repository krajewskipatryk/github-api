package com.githubintegration.githubapi.github.service;

import com.githubintegration.githubapi.github.exceptions.GithubApiEmptyResultSetException;
import com.githubintegration.githubapi.github.exceptions.GithubClientException;
import com.githubintegration.githubapi.github.exceptions.RequestLimitExceeded;
import com.githubintegration.githubapi.github.model.github.GithubBranch;
import com.githubintegration.githubapi.github.model.github.GithubRepository;
import com.githubintegration.githubapi.github.util.JsonUtil;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;

import java.util.List;

class GithubHttpClientImpl implements GithubHttpClient {
    private final RestClient githubClient = RestClient.create();

    @Override
    public List<GithubRepository> getRepoList(String username) {
        return githubClient
                .get()
                .uri("https://api.github.com/users/{username}/repos", username)
                .exchange(this::handleGetRepoList);
    }

    @Override
    public List<GithubBranch> getBranchList(String username, String repository) {
        return githubClient
                .get()
                .uri("https://api.github.com/repos/{username}/{repository}/branches", username, repository)
                .exchange(this::handleGetBranchList);
    }

    @Override
    public String getUser(String username) {
        return githubClient
                .get()
                .uri("https://api.github.com/users/{username}", username)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    validateIfRequestLimitExceeded(response.getStatusCode());
                    if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                        throw new GithubApiEmptyResultSetException(HttpStatus.NOT_FOUND,
                                STR."User with username = \{username} does not exist");
                    }
                })
                .body(String.class);
    }

    private List<GithubBranch> handleGetBranchList(HttpRequest request, RestClient.RequestHeadersSpec.ConvertibleClientHttpResponse response) {
        try {
            validateIfRequestLimitExceeded(response.getStatusCode());

            return getMappedList(response.bodyTo(String.class), GithubBranch.class);

        } catch (Exception e) {
            throw new GithubClientException(HttpStatus.BAD_REQUEST, STR."Error occurred when parsing Branches: \{e.getMessage()}");
        }
    }

    private List<GithubRepository> handleGetRepoList(HttpRequest request, RestClient.RequestHeadersSpec.ConvertibleClientHttpResponse response) {
        try {
            validateIfRequestLimitExceeded(response.getStatusCode());

            return getMappedList(response.bodyTo(String.class), GithubRepository.class);
        }
        catch (RequestLimitExceeded e) {
            throw new RequestLimitExceeded(HttpStatus.TOO_MANY_REQUESTS, "Github request limit exceeded");
        }
        catch (Exception e) {
            throw new GithubClientException(HttpStatus.BAD_REQUEST, STR."Error occurred when parsing Repositories: \{e.getMessage()}");
        }
    }

    private void validateIfRequestLimitExceeded(HttpStatusCode status) {
//        if (status == HttpStatus.FORBIDDEN) {
//            throw new RequestLimitExceeded();
//        }
    }

    private <T> T getMappedList(String json, Class<?> clazz) {
        return JsonUtil.unmarshalJsonCollection(json, List.class, clazz);
    }
}
