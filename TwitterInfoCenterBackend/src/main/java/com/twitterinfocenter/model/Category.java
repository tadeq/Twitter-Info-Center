package com.twitterinfocenter.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.List;

@Value
public class Category {
    @JsonProperty("name")
    String name;

    @JsonProperty("usernames")
    List<String> usernames;

    public String getName() {
        return name;
    }

    public List<String> getUsernames() {
        return usernames;
    }
}
