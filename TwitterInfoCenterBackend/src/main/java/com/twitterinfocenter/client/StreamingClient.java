package com.twitterinfocenter.client;

import com.twitterinfocenter.service.ApiAccessDataProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class StreamingClient {

    private final ApiAccessDataProvider provider;

    @Autowired
    public StreamingClient(ApiAccessDataProvider provider) {
        this.provider = provider;
    }

    public void testStreaming() {
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.setOAuthConsumerKey(provider.getApiKey())
                .setOAuthConsumerSecret(provider.getApiKeySecret())
                .setOAuthAccessToken(provider.getAccessToken())
                .setOAuthAccessTokenSecret(provider.getAccessTokenSecret());
        TwitterStream twitterStream = new TwitterStreamFactory(configurationBuilder.build()).getInstance();
        twitterStream.onStatus(status -> {
            System.out.println(status);
        });
        twitterStream.filter(new FilterQuery(2899773086L));
    }
}
