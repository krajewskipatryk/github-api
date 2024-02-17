package com.githubintegration.githubapi.github.service;

import com.githubintegration.githubapi.github.exceptions.GithubApiEmptyResultSetException;
import com.githubintegration.githubapi.github.exceptions.GithubClientException;
import com.githubintegration.githubapi.github.model.api.Branch;
import com.githubintegration.githubapi.github.model.api.Repository;
import com.githubintegration.githubapi.github.model.github.GithubBranch;
import com.githubintegration.githubapi.github.model.github.GithubRepository;
import com.githubintegration.githubapi.github.util.JsonUtils;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;

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


    private List<Branch> handleGetBranchList(HttpRequest request, RestClient.RequestHeadersSpec.ConvertibleClientHttpResponse response) {
        try {
            List<GithubBranch> branches = getMappedList(response.bodyTo(String.class), GithubBranch.class);

            return branches.stream()
                    .map(branch -> new Branch(branch.name(), branch.commit().sha()))
                    .toList();
        } catch (Exception e) {
            throw new GithubClientException(HttpStatus.BAD_REQUEST, STR."Error occurred when parsing Branches: \{e.getMessage()}");
        }
    }

    private List<Repository> handleGetRepoList(HttpRequest request, RestClient.RequestHeadersSpec.ConvertibleClientHttpResponse response) {
        try {
            List<GithubRepository> repositories = getMappedList(response.bodyTo(String.class), GithubRepository.class);

            return repositories.stream()
                    .filter(repo -> repo.fork().equals(false))
                    .map(repo -> new Repository(repo.name(), new ArrayList<>()))
                    .toList();
        } catch (Exception e) {
            throw new GithubClientException(HttpStatus.BAD_REQUEST, STR."Error occurred when parsing Repositories: \{e.getMessage()}");
        }
    }

    private <T> T getMappedList(String json, Class<?> clazz) {
        return JsonUtils.unmarshalJsonCollection(json, List.class, clazz);
    }
}
