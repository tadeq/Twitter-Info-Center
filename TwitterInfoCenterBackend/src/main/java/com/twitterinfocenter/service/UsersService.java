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
import java.util.stream.Collectors;

@Service
public class UsersService {

    private static final String DATA_NODE = "data";
    private static final String ERRORS_NODE = "errors";
    private static final String MESSAGE_NODE = "message";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final TwitterClient twitterClient;

    private List<User> users = new ArrayList<>();

    @Autowired
    public UsersService(TwitterClient twitterClient) {
        this.twitterClient = twitterClient;
    }

    public List<User> getUsersByUsernames(List<String> usernames) {
        return users.stream()
                .filter(user -> usernames.contains(user.getName()))
                .collect(Collectors.toList());
    }

    public List<User> addUsers(List<String> usernames) {
        HttpResponse<String> response = twitterClient.getUsersByUsernames(usernames);
        try {
            JsonNode jsonTree = OBJECT_MAPPER.readTree(response.body());
            checkResponseCode(response, jsonTree);
            String dataResponseNode = jsonTree.get(DATA_NODE).toString();
            List<User> users = OBJECT_MAPPER.readValue(dataResponseNode, OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, User.class));
            this.users.addAll(users);
            return users;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new TwitterInfoCenterException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Problem when processing response: " + e.getMessage());
        }
    }

    public void deleteUsers(List<String> usernames) {
        //TODO add unsubscribing users from twitter
        this.users = this.users.stream()
                .filter(user -> !usernames.contains(user.getName()))
                .collect(Collectors.toList());
    }

    private void checkResponseCode (HttpResponse<String> response, JsonNode jsonTree) {
        if (HttpStatus.valueOf(response.statusCode()).isError()) {
            throw new TwitterInfoCenterException(response.statusCode(), buildErrorMessage(jsonTree));
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

    private void handleJsonProcessingException(Exception e) {
        e.printStackTrace();
        throw new TwitterInfoCenterException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Problem when processing response: " + e.getMessage());
    }
}
