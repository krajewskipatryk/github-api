package com.githubintegration.githubapi.github;


import com.githubintegration.githubapi.github.model.api.Branch;
import com.githubintegration.githubapi.github.model.api.Repository;

import java.util.List;

public interface GithubHttpService {
    List<Repository> getRepoList(String username, boolean includeForks);
    List<Branch> getBranchList(String username, String repository);
    void doesUserExist(String username);
}
