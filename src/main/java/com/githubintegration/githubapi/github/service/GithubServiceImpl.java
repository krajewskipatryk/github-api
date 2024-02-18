package com.githubintegration.githubapi.github.service;

import com.githubintegration.githubapi.github.model.api.GithubRepositories;
import com.githubintegration.githubapi.github.model.api.Repository;

import java.util.ArrayList;
import java.util.List;

class GithubServiceImpl implements GithubService {
    private final GithubHttpService githubHttpService;

    public GithubServiceImpl(GithubHttpService githubHttpService) {
        this.githubHttpService = githubHttpService;
    }

    @Override
    public GithubRepositories getGithubReposByUsername(final String username) {
        final List<Repository> repositories = new ArrayList<>();

        if (githubHttpService.doesUserExist(username)) {
            repositories.addAll(githubHttpService.getRepoList(username));
            repositories.forEach(repo -> getRepositoryBranches(username, repo));
        }

        return new GithubRepositories(username, repositories);
    }

    private void getRepositoryBranches(String username, Repository repository) {
        repository.branches().addAll(githubHttpService.getBranchList(username, repository.name()));
    }
}
