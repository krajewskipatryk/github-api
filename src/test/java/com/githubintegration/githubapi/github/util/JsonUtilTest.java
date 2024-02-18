package com.githubintegration.githubapi.github.util;

import com.githubintegration.githubapi.github.GithubJsonTestValues;
import com.githubintegration.githubapi.github.model.api.Repository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class JsonUtilTest {
    @Test
    void unmarshalJsonCollection_passingJsonArray_shouldReturnListOfRepositories() {
        List<Repository> repositories = JsonUtil.unmarshalJsonCollection(GithubJsonTestValues.REPOSITORIES_JSON, List.class, Repository.class);

        assertFalse(repositories.isEmpty());
    }
}


