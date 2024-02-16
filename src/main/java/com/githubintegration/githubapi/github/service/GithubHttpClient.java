package com.githubintegration.githubapi.github.service;


import com.githubintegration.githubapi.github.model.api.Branch;
import com.githubintegration.githubapi.github.model.api.Repository;

import java.util.List;

public interface GithubHttpClient {
    List<Repository> getRepoList(String username);
    List<Branch> getBranchList(String username, String repository);
    boolean doesUserExist(String username);
}
