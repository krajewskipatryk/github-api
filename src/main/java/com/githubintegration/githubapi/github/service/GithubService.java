package com.githubintegration.githubapi.github.service;

import com.githubintegration.githubapi.github.model.api.GithubRepositories;

public interface GithubService {
    GithubRepositories getGithubReposByUsername(String user);
}
