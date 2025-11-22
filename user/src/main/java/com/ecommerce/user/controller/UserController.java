package com.ecommerce.user.controller;


import com.ecommerce.user.dto.UserRequest;
import com.ecommerce.user.dto.UserResponse;
import com.ecommerce.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/users")
@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
//    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        System.out.println("REQUEST RECIVE");
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping("/{id}")

    public ResponseEntity<UserResponse> getUser(@PathVariable String id) {
        log.info("Rquest recived for user: {}", id);
        log.trace("This is trace level - Very details log");
        log.debug("This is debug level - Very details loguse for develpment debugging");
        log.info("This is INFO level - General system information");
        log.warn("This is WARN level - Something might me might");
        log.error("This is ERROR level - Something Failed");
        return userService.fetchUser(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<String> addUser(@RequestBody UserRequest userRequest) {
        userService.addUser(userRequest);
        return ResponseEntity.ok("User added");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatedUser(@PathVariable String id,
                                              @RequestBody UserRequest updatedUserRequest) {
        boolean updated = userService.updateUser(id, updatedUserRequest);
        if (updated) {
            return ResponseEntity.ok("User updated");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
