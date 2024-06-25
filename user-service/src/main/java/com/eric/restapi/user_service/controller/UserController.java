package com.eric.restapi.user_service.controller;

import com.eric.restapi.user_service.model.PatchUserRequest;
import com.eric.restapi.user_service.model.User;
import com.eric.restapi.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") String userId) {
        return userService.getUser(userId)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/users")
    public ResponseEntity<User> postUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> putUser(@PathVariable String id, @RequestBody User user) {
        return userService.updateUser(id, user)
                .map(u -> new ResponseEntity<>(u, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<User> patchUser(@PathVariable("id") String id, @RequestBody User user) {
        return userService.patchUser(id, user)
                .map(u -> new ResponseEntity<>(u, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable String id) {
        return userService.deleteUser(id)
            .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/test1")
    public String test1(@RequestParam String id, @RequestParam String name) {
        return id.concat("-").concat(name);
    }

    @GetMapping("/test2")
    public String test2(@RequestHeader HttpHeaders headers) {
        return headers.getHost().getHostName() + "-" + headers.getAccept();
    }

    @GetMapping("/test3")
    public String test3(@RequestHeader Map<String, String> headers) {
        StringBuilder strBuilder = new StringBuilder();
        headers.forEach(
                (key, value) -> strBuilder.append(key).append(":").append(value)
        );
        return strBuilder.toString();
    }

}
