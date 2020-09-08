package com.twitterinfocenter.client;

import com.twitterinfocenter.service.ApiAccessDataProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Component
public class TwitterApiClient {
    private static final String HTTPS = "https";
    private static final String BASE_URI = "api.twitter.com";
    private static final String V2_PATH = "2";
    private static final String USERS_PATH = "users";
    private static final String BY_PATH = "by";
    private static final String USERNAMES_QUERY_PARAM = "usernames";

    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder().build();

    private final ApiAccessDataProvider accessProvider;

    @Autowired
    public TwitterApiClient(ApiAccessDataProvider accessProvider) {
        this.accessProvider = accessProvider;
    }

    public HttpResponse<String> getUsersByUsernames(List<String> usernames) {
        URI uri = getBaseUriComponentsBuilder()
                .pathSegment(V2_PATH, USERS_PATH, BY_PATH)
                .queryParam(USERNAMES_QUERY_PARAM, String.join(",", usernames))
                .build()
                .toUri();

        HttpRequest request = getUriWithHeaders(uri)
                .GET()
                .build();

        return sendRequest(request);
    }

    private UriComponentsBuilder getBaseUriComponentsBuilder() {
        return UriComponentsBuilder.newInstance()
                .scheme(HTTPS)
                .host(BASE_URI);
    }

    private HttpRequest.Builder getUriWithHeaders(URI uri) {
        return HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + accessProvider.getBearerToken());
    }

    private HttpResponse<String> sendRequest(HttpRequest request) {
        try {
            return HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
}
