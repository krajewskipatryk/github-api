package com.githubintegration.githubapi.github;

import com.githubintegration.githubapi.github.model.api.Branch;
import com.githubintegration.githubapi.github.model.api.GithubRepositories;
import com.githubintegration.githubapi.github.model.api.Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GithubServiceImplTest {
    private final String REPOSITORY_NAME = "test-repo";
    private final String BRANCH_NAME = "test-branch";
    private final String SHU = "test-shu";

    private GithubConfig githubConfig = new GithubConfig();
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
        final String username = "username";
        List<Repository> repositories = List.of(
                buildTestRepository(REPOSITORY_NAME)
        );
        List<Branch> branches = List.of(
                buildTestBranch(BRANCH_NAME, SHU)
        );

        doNothing().when(githubHttpService).doesUserExist(eq(username));
        doReturn(repositories).when(githubHttpService).getRepoList(eq(username), eq(false));
        doReturn(branches).when(githubHttpService).getBranchList(eq(username), eq(REPOSITORY_NAME));

        // When
        GithubRepositories result = githubService.getGithubReposByUsername(username);

        // Then
        assertAll(
                () -> assertEquals(username, result.username()),
                () -> assertEquals(1, result.repositories().size()),
                () -> assertEquals(REPOSITORY_NAME, result.repositories().getFirst().name()),
                () -> assertEquals(BRANCH_NAME, result.repositories().getFirst().branches().getFirst().branchName()),
                () -> assertEquals(SHU, result.repositories().getFirst().branches().getFirst().shu()),
                () -> verify(githubHttpService).doesUserExist(eq(username)),
                () -> verify(githubHttpService).getRepoList(eq(username), eq(false)),
                () -> verify(githubHttpService).getBranchList(eq(username), eq(REPOSITORY_NAME)),
                () -> verifyNoMoreInteractions(githubHttpService)
        );
    }

    private Repository buildTestRepository(String name) {
        return new Repository(name, new ArrayList<>());
    }

    private Branch buildTestBranch(String branch, String shu) {
        return new Branch(branch, shu);
    }
}