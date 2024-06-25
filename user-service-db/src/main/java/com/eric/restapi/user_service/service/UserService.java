package com.eric.restapi.user_service.service;

import com.eric.restapi.user_service.controller.UserDTO;
import com.eric.restapi.user_service.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getUsers();

    User getUser(Long id);

    void deleteUser(Long id);

    User createUser(UserDTO user);

    User putUser(Long id, UserDTO user);

    User updateUser(Long id, UserDTO user);
}
