package com.twitterinfocenter.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class User {
    @JsonProperty("id")
    long id;

    @JsonProperty("name")
    String name;

    @JsonProperty("username")
    String username;
}
