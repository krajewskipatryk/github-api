package com.githubintegration.githubapi.github;

import com.githubintegration.githubapi.github.model.github.GithubBranch;
import com.githubintegration.githubapi.github.model.github.GithubRepository;

import java.util.List;

public interface GithubHttpClient {
    List<GithubRepository> getGithubRepoList(String username);
    List<GithubBranch> getGithubBranchList(String username, String repository);
    String getGithubUser(String username);
}
