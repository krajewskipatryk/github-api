package com.githubintegration.githubapi.github.service;

import com.githubintegration.githubapi.github.model.api.Branch;
import com.githubintegration.githubapi.github.model.api.Repository;

import java.util.ArrayList;
import java.util.List;

class GithubHttpServiceImpl implements GithubHttpService {
    private final GithubHttpClient githubHttpClient;

    public GithubHttpServiceImpl(GithubHttpClient githubHttpClient) {
        this.githubHttpClient = githubHttpClient;
    }

    @Override
    public List<Repository> getRepoList(String username) {
        return githubHttpClient.getRepoList(username).stream()
                .filter(repo -> repo.fork().equals(false))
                .map(repo -> new Repository(repo.name(), new ArrayList<>()))
                .toList();
    }

    @Override
    public List<Branch> getBranchList(String username, String repository) {
        return githubHttpClient.getBranchList(username, repository).stream()
                .map(branch -> new Branch(branch.name(), branch.commit().sha()))
                .toList();
    }

    @Override
    public boolean doesUserExist(String username) {
        return githubHttpClient.getUser(username) != null;
    }
}
