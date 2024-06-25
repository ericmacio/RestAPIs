package com.eric.restapi.user_service.service;

import com.eric.restapi.user_service.model.User;
import com.eric.restapi.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getUsers() {
        return userRepository.getUsers();
    }

    @Override
    public Optional<User> getUser(String id) {
        return userRepository.getUser(id);
    }

    @Override
    public Optional<User> deleteUser(String id) {
        return userRepository.deleteUser(id);
    }

    @Override
    public Optional<User> patchUser(String id, User user) {
        return modifyUser(id, user);
    }

    @Override
    public User createUser(User user) {
        return userRepository.saveUser(user);
    }

    @Override
    public Optional<User> updateUser(String id, User user) {
        Optional<User> optUpdatedUser = modifyUser(id, user);
        if (optUpdatedUser.isEmpty()) {
            return Optional.of(userRepository.saveUser(user));
        } else
            return optUpdatedUser;
    }

    private Optional<User> modifyUser(String id, User user) {
        User updatedUser = null;
        Optional<User> optCurrUser = userRepository.getUser(id);
        if (optCurrUser.isPresent()) {
            User currUser = optCurrUser.get();
            updatedUser = userRepository.patchUser(id,
                    new User(
                            currUser.getId(),
                            user.getFirstName() != null ? user.getFirstName() : currUser.getFirstName(),
                            user.getLastName() != null ? user.getLastName() : currUser.getLastName(),
                            user.getEmail() != null ? user.getEmail() : currUser.getEmail())
            );
        }
        return Optional.ofNullable(updatedUser);
    }

}
