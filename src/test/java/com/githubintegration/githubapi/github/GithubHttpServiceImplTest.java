package com.githubintegration.githubapi.github;

import com.githubintegration.githubapi.github.model.api.Branch;
import com.githubintegration.githubapi.github.model.api.Repository;
import com.githubintegration.githubapi.github.model.github.GithubBranch;
import com.githubintegration.githubapi.github.model.github.GithubRepository;
import com.githubintegration.githubapi.github.util.JsonUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GithubHttpServiceImplTest {
    private final GithubConfig githubConfig = new GithubConfig();
    @Mock
    private GithubHttpClient githubHttpClient;
    private GithubHttpService githubHttpService;

    @BeforeEach
    void setUp() {
        githubHttpService = githubConfig.githubHttpService(githubHttpClient);
    }

    @Test
    void getRepoList_givenUsernameAndIncludeForksFalse_shouldReturnGithubReposListWithoutForks() {
        // Given
        final String username = "username";
        final boolean includeForks = false;

        List<GithubRepository> repositories = JsonUtil.unmarshalJsonCollection(GithubJsonTestValues.REPOSITORIES_JSON, List.class, GithubRepository.class);
        doReturn(repositories).when(githubHttpClient).getGithubRepoList(username);

        // When
        List<Repository> result = githubHttpService.getRepoList(username, includeForks);

        // Then
        assertAll(
                () -> assertEquals(6, result.size()),
                () -> result.forEach(repo -> assertNotNull(repo.name(), "Repository name is null")),
                () -> result.forEach(repo -> assertNotNull(repo.branches(), "Repository branches value is null"))
        );

    }

    @ParameterizedTest
    @MethodSource("provideBranchJsonValues")
    void getBranchList_givenExistingUsernameAndRepository_shouldReturnBranchesForProvidedRepo(String branchJson) {
        // Given
        final String username = "username";
        final String repositoryName = "repository name";
        List<Branch> branches = JsonUtil.unmarshalJsonCollection(branchJson, List.class, GithubBranch.class);

        doReturn(branches).when(githubHttpClient).getGithubBranchList(eq(username), eq(repositoryName));

        // When
        List<Branch> result = githubHttpService.getBranchList(username, repositoryName);

        // Then
        assertAll(
                () -> assertFalse(result.isEmpty()),
                () -> result.forEach(branch -> assertNotNull(branch.branchName(), "Branch name is null")),
                () -> result.forEach(branch -> assertNotNull(branch.sha(), "Branch sha is null")),
                () -> verify(githubHttpClient).getGithubBranchList(eq(username), eq(repositoryName)),
                () -> verifyNoMoreInteractions(githubHttpClient)
        );
    }


    private static Stream<Arguments> provideBranchJsonValues() {
        return Stream.of(
                Arguments.of(GithubJsonTestValues.BRANCH_JSON_CALCULATOR),
                Arguments.of(GithubJsonTestValues.BRANCH_JSON_HOMEORGANIZER),
                Arguments.of(GithubJsonTestValues.BRANCH_JSON_LIVECHAT),
                Arguments.of(GithubJsonTestValues.BRANCH_JSON_PC_CONFIGURATOR),
                Arguments.of(GithubJsonTestValues.BRANCH_JSON_DESIGN_PATTERNS_PG),
                Arguments.of(GithubJsonTestValues.BRANCH_JSON_KAFKA_CONNECT_TOOLS),
                Arguments.of(GithubJsonTestValues.BRANCH_JSON_SOCIAL_CARD)
        );
    }
}