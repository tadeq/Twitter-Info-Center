package com.twitterinfocenter.controller;

import com.twitterinfocenter.client.StreamingClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StreamingController {

    private final StreamingClient streamingClient;

    @Autowired
    public StreamingController(StreamingClient streamingClient) {
        this.streamingClient = streamingClient;
    }

    @GetMapping("/streaming")
    public ResponseEntity<?> get() {
        System.out.println("START STREAMING");
        streamingClient.testStreaming();
        return ResponseEntity.ok().build();
    }
}
