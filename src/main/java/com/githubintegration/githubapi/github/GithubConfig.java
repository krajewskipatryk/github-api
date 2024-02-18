package com.githubintegration.githubapi.github;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class GithubConfig {
    @Bean
    GithubHttpService githubHttpService(GithubHttpClient githubHttpClient) {
        return new GithubHttpServiceImpl(githubHttpClient);
    }

    @Bean
    GithubService githubService(GithubHttpService githubHttpService) {
        return new GithubServiceImpl(githubHttpService);
    }

    @Bean
    GithubHttpClient githubHttpClient() {
        return new GithubHttpClientImpl();
    }
}
