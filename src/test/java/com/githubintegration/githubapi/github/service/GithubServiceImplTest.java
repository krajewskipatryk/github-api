package com.githubintegration.githubapi.github.service;

import com.githubintegration.githubapi.github.model.api.GithubRepositories;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GithubServiceImplTest {
    GithubConfig githubConfig = new GithubConfig();
    @Mock
    private GithubHttpService githubHttpService;
    private GithubService githubService;

    @BeforeEach
    void setUp() {
        githubService = githubConfig.githubService(githubHttpService);
    }

    @Test
    void getGithubReposByUsername_givenExistingUsername_shouldReturnMappedRepositoriesList() {
        // Given
        final String username = "krajewskipatryk";

        // When
        GithubRepositories result = githubService.getGithubReposByUsername(username);

        assertAll(
                () -> assertEquals(username, result.username()),
                () -> assertFalse(result.repositories().isEmpty())
        );


    }
}