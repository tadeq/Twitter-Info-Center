package com.twitterinfocenter.client;

import com.twitterinfocenter.service.ApiAccessDataProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import twitter4j.*;
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
        twitterStream.addListener(new StatusListener() {
            @Override
            public void onException(Exception ex) {

            }

            @Override
            public void onStatus(Status status) {
                System.out.println(status.getText());
            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {

            }

            @Override
            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {

            }

            @Override
            public void onScrubGeo(long userId, long upToStatusId) {

            }

            @Override
            public void onStallWarning(StallWarning warning) {

            }
        });
        twitterStream.filter(new FilterQuery(2899773086L));
    }
}
