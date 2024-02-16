package com.githubintegration.githubapi.github.service;

import com.githubintegration.githubapi.github.model.api.GithubRepositories;
import com.githubintegration.githubapi.github.model.api.Repository;

import java.util.ArrayList;
import java.util.List;

class GithubServiceImpl implements GithubService {
    private final GithubHttpClient githubHttpClient;

    @Override
    public GithubRepositories getGithubReposByUsername(final String username) {
        final List<Repository> repositories = new ArrayList<>();

        if (githubHttpClient.doesUserExist(username)) {
            repositories.addAll(githubHttpClient.getRepoList(username));
            repositories.forEach(repo -> getRepositoryBranches(username, repo));
        }

        return new GithubRepositories(username, repositories);
    }

    public GithubServiceImpl(GithubHttpClient githubHttpClient) {
        this.githubHttpClient = githubHttpClient;
    }

    private void getRepositoryBranches(String username, Repository repository) {
        repository.branches().addAll(githubHttpClient.getBranchList(username, repository.name()));
    }
}
