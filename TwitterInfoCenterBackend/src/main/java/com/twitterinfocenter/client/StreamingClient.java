package com.twitterinfocenter.client;

import com.twitterinfocenter.service.ApiAccessDataProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import twitter4j.FilterQuery;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

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
        twitterStream.onStatus(System.out::println);
        twitterStream.filter(new FilterQuery(2899773086L));
    }
}
