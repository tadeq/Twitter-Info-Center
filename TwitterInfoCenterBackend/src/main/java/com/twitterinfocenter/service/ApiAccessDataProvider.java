package com.twitterinfocenter.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:access.properties")
public class ApiAccessDataProvider {

    @Value("${bearer.token}")
    private String bearerToken;

    @Value("${api.key}")
    private String apiKey;

    @Value("${api.key.secret}")
    private String apiKeySecret;

    @Value("${access.token}")
    private String accessToken;

    @Value("${access.token.secret}")
    private String accessTokenSecret;

    public String getBearerToken() {
        return bearerToken;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getApiKeySecret() {
        return apiKeySecret;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getAccessTokenSecret() {
        return accessTokenSecret;
    }
}
