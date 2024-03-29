package com.githubintegration.githubapi.github;

import com.githubintegration.githubapi.github.model.api.Branch;
import com.githubintegration.githubapi.github.model.api.Repository;
import com.githubintegration.githubapi.github.model.github.GithubBranch;
import com.githubintegration.githubapi.github.model.github.GithubRepository;

import java.util.ArrayList;
import java.util.List;

class GithubHttpServiceImpl implements GithubHttpService {
    private final GithubHttpClient githubHttpClient;

    public GithubHttpServiceImpl(GithubHttpClient githubHttpClient) {
        this.githubHttpClient = githubHttpClient;
    }

    @Override
    public List<Repository> getRepoList(String username, boolean includeForks) {
        return githubHttpClient.getGithubRepoList(username).stream()
                .filter(repo -> repo.fork().equals(includeForks))
                .map(this::mapToRepository)
                .toList();
    }

    @Override
    public List<Branch> getBranchList(String username, String repository) {
        return githubHttpClient.getGithubBranchList(username, repository).stream()
                .map(this::mapToBranch)
                .toList();
    }

    @Override
    public void doesUserExist(String username) {
        githubHttpClient.getGithubUser(username);
    }

    private Repository mapToRepository(GithubRepository repo) {
        return new Repository(repo.name(), new ArrayList<>());
    }

    private Branch mapToBranch(GithubBranch branch) {
        return new Branch(branch.name(), branch.commit().sha());
    }
}
