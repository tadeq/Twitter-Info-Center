package com.twitterinfocenter.controller;

import com.twitterinfocenter.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UsersController {


    private final UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping
    public ResponseEntity<?> getUsers(@RequestParam List<String> usernames) {
        return ResponseEntity.ok(usersService.getUsersByUsernames(usernames));
    }

    @PostMapping
    public ResponseEntity<?> addSubscribedUsers(@RequestParam List<String> usernames) {
        return ResponseEntity.ok(usersService.addUsers(usernames));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteSubscribedUsers(@RequestParam List<String> usernames) {
        usersService.deleteUsers(usernames);
        return ResponseEntity.noContent().build();
    }
}
