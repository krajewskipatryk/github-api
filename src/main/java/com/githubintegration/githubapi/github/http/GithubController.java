package com.githubintegration.githubapi.github.http;

import com.githubintegration.githubapi.github.GithubService;
import com.githubintegration.githubapi.github.model.api.GithubRepositories;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/github")
class GithubController {
    private final GithubService githubService;

    public GithubController(GithubService githubService) {
        this.githubService = githubService;
    }

    @GetMapping(path = "/{username}/repos", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<GithubRepositories> getReposList(@PathVariable String username) {
        return ResponseEntity.ok()
                .body(githubService.getGithubReposByUsername(username));
    }
}
