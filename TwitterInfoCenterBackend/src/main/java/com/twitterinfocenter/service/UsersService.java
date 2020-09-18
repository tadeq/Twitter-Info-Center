package com.twitterinfocenter.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twitterinfocenter.client.TwitterClient;
import com.twitterinfocenter.exception.TwitterInfoCenterException;
import com.twitterinfocenter.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsersService {

    private static final String DATA_NODE = "data";
    private static final String ERRORS_NODE = "errors";
    private static final String MESSAGE_NODE = "message";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private TwitterClient twitterClient;

    @Autowired
    public UsersService(TwitterClient twitterClient) {
        this.twitterClient = twitterClient;
    }

    public List<User> getUsersByUsernames(List<String> usernames) {
        HttpResponse<String> response = twitterClient.getUsersByUsernames(usernames);
        try {
            JsonNode jsonTree = OBJECT_MAPPER.readTree(response.body());
            if (HttpStatus.valueOf(response.statusCode()).isError()) {
                throw new TwitterInfoCenterException(response.statusCode(), buildErrorMessage(jsonTree));
            }
            String dataResponseNode = jsonTree.get(DATA_NODE).toString();
            return OBJECT_MAPPER.readValue(dataResponseNode, OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, User.class));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new TwitterInfoCenterException(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Problem when processing response " + e.getMessage());
        }
    }

    private String buildErrorMessage(JsonNode jsonTree) {
        JsonNode arrayNode = jsonTree.get(ERRORS_NODE);
        List<String> messages = new ArrayList<>();
        for (JsonNode errorNode : arrayNode) {
            messages.add(errorNode.get(MESSAGE_NODE).toPrettyString());
        }
        return String.join(";\n", messages);
    }
}
