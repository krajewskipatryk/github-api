package com.githubintegration.githubapi.github.model.api;

import java.util.List;

public record GithubRepositories(String username, List<Repository> repositories) {
}
