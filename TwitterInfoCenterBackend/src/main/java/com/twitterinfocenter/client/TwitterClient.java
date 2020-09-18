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
public class TwitterClient {
    private static final String HTTPS = "https";

    private static final String API_BASE_URI = "api.twitter.com";
    private static final String V2_PATH = "2";
    private static final String USERS_PATH = "users";
    private static final String BY_PATH = "by";
    private static final String USERNAMES_PARAM = "usernames";
    private static final String TWEETS_PARAM = "tweets";
    private static final String RECENT_PARAM = "recent";
    private static final String QUERY_PARAM = "query";
    private static final String QUERY_FROM_VALUE = "from:";
    private static final String MAX_RESULTS_PARAM = "max_results";
    private static final String MAX_RESULTS_VALUE = "100";
    private static final String TWEET_FIELDS_PARAM = "tweet.fields";
    private static final String TWEET_FIELDS_VALUE = "created_at";

    private static final String PUBLISH_BASE_URI = "publish.twitter.com";
    private static final String OEMBED_PATH = "oembed";

    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder().build();

    private final ApiAccessDataProvider accessProvider;

    @Autowired
    public TwitterClient(ApiAccessDataProvider accessProvider) {
        this.accessProvider = accessProvider;
    }

    public HttpResponse<String> getUsersByUsernames(List<String> usernames) {
        URI uri = getBaseUriComponentsBuilder(API_BASE_URI)
                .pathSegment(V2_PATH, USERS_PATH, BY_PATH)
                .queryParam(USERNAMES_PARAM, String.join(",", usernames))
                .build()
                .toUri();

        HttpRequest request = getUriWithHeaders(uri)
                .GET()
                .build();

        return sendRequest(request);
    }

    public HttpResponse<String> getRecentTweets(String username) {
        URI uri = getBaseUriComponentsBuilder(API_BASE_URI)
                .pathSegment(V2_PATH, TWEETS_PARAM, RECENT_PARAM)
                .queryParam(QUERY_PARAM, QUERY_FROM_VALUE + username)
                .queryParam(MAX_RESULTS_PARAM, MAX_RESULTS_VALUE)
                .queryParam(TWEET_FIELDS_PARAM, TWEET_FIELDS_VALUE)
                .build()
                .toUri();

        HttpRequest request = getUriWithHeaders(uri)
                .GET()
                .build();

        return sendRequest(request);
    }

    private UriComponentsBuilder getBaseUriComponentsBuilder(String host) {
        return UriComponentsBuilder.newInstance()
                .scheme(HTTPS)
                .host(host);
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
