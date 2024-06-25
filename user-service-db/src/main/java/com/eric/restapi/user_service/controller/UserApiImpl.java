package com.eric.restapi.user_service.controller;

import com.eric.restapi.user_service.exceptions.NotFoundException;
import com.eric.restapi.user_service.model.User;
import com.eric.restapi.user_service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Pattern;

@RestController
public class UserApiImpl implements UserApi {

    private final UserService userService;

    public UserApiImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<User> getUser(@PathVariable("id") Long userId) {
        return new ResponseEntity<>(userService.getUser(userId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<User> postUser(@RequestBody @Valid UserDTO userDTO) {
        return new ResponseEntity<>(userService.createUser(userDTO), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<User> putUser(@PathVariable Long id, @RequestBody @Valid UserDTO updatedUserDTO) {
        User user = userService.putUser(id, updatedUserDTO);
        if(user.getId().equals(id))
            return new ResponseEntity<>(user, HttpStatus.OK);
        else
            return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<User> patchUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        validateForUpdate(userDTO);
        return new ResponseEntity<>(userService.updateUser(id, userDTO), HttpStatus.OK);
    }

    @Override
    @Transactional
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    private void validateForUpdate(UserDTO UserDTO) {
        if(UserDTO.getEmail() != null && UserDTO.getEmail().isBlank())
            throw new IllegalArgumentException("Email cannot be blank");
        if(UserDTO.getEmail() != null && !Pattern.matches("^(.+)@(.+)$", UserDTO.getEmail()))
            throw new IllegalArgumentException("Email is not valid");
        if(UserDTO.getFirstName() != null && UserDTO.getFirstName().isBlank())
            throw new IllegalArgumentException("First name cannot be blank");
        if(UserDTO.getLastName() != null && UserDTO.getLastName().isBlank())
            throw new IllegalArgumentException("Last name cannot be blank");
    }

}
