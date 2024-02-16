package com.githubintegration.githubapi.github.model.api;

import java.util.List;

public record Repository(String name, List<Branch> branches) {
}
