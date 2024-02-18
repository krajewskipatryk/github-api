package com.githubintegration.githubapi.github.service;

import com.githubintegration.githubapi.github.model.github.GithubBranch;
import com.githubintegration.githubapi.github.model.github.GithubRepository;

import java.util.List;

public interface GithubHttpClient {
    List<GithubRepository> getRepoList(String username);
    List<GithubBranch> getBranchList(String username, String repository);
    String getUser(String username);
}
