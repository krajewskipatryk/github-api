package com.githubintegration.githubapi.github.model.api;

import java.util.List;

public class Repository {
    private String name;
    private List<Branch> branches;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Branch> getBranches() {
        return branches;
    }

    public void setBranches(List<Branch> branches) {
        this.branches = branches;
    }
}
