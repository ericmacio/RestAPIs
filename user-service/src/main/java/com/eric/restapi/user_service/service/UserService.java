package com.eric.restapi.user_service.service;

import com.eric.restapi.user_service.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getUsers();

    Optional<User> getUser(String id);

    Optional<User> deleteUser(String id);

    Optional<User> patchUser(String id, User user);

    User createUser(User user);

    Optional<User> updateUser(String id, User user);
}
