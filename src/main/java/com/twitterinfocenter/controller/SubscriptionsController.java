package com.twitterinfocenter.controller;

import com.twitterinfocenter.client.StreamingClient;
import com.twitterinfocenter.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SubscriptionsController {

    private final StreamingClient streamingClient;

    private final UsersService usersService;

    @Autowired
    public SubscriptionsController(StreamingClient streamingClient, UsersService usersService) {
        this.streamingClient = streamingClient;
        this.usersService = usersService;
    }

    @GetMapping("/")
    public ResponseEntity<?> get() {
        System.out.println("START STREAMING");
        streamingClient.testStreaming();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUsers(@RequestParam List<String> usernames) {
        return ResponseEntity.ok(usersService.getUsersByUsernames(usernames));
    }

    @PostMapping("/users")
    public ResponseEntity<?> addSubscribedUsers(@RequestParam List<String> usernames) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/users")
    public ResponseEntity<?> deleteSubscribedUsers(@RequestParam List<String> usernames) {
        return ResponseEntity.ok().build();
    }
}
