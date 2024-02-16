package com.githubintegration.githubapi.github.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class GithubConfig {
    @Bean
    GithubHttpClient githubHttpClient() {
        return new GithubHttpClientImpl();
    }

    @Bean
    GithubService githubService(GithubHttpClient githubHttpClient) {
        return new GithubServiceImpl(githubHttpClient);
    }


}
