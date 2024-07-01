package com.eric.restapi.user_service.service;

import com.eric.restapi.user_service.controller.UserDTO;
import com.eric.restapi.user_service.exceptions.NotFoundException;
import com.eric.restapi.user_service.model.User;
import com.eric.restapi.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(Long id) {
        return userRepository.findUserById(id)
                .orElseThrow(() -> new NotFoundException("User not found for id: " + id + ". Cannot get"));
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found for email: " + email + ". Cannot get"));
    }


    @Override
    public void deleteUser(Long id) {
        userRepository.deleteUserById(id)
                .filter(i -> i > 0)
                .orElseThrow(() -> new NotFoundException("User not found for id: " + id + ". Cannot delete"));
    }

    @Override
    public User createUser(UserDTO userDTO) {
        return userRepository.save(new User(
                userDTO.getFirstName(),
                userDTO.getLastName(),
                userDTO.getEmail())
        );
    }

    @Override
    public User putUser(Long id, UserDTO userDTO) {
        return userRepository.save(new User(
                id,
                userDTO.getFirstName(),
                userDTO.getLastName(),
                userDTO.getEmail())
        );
    }

    @Override
    public User updateUser(Long id, UserDTO userDTO) {
        User currUser = userRepository.findUserById(id)
                .orElseThrow(() -> new NotFoundException("User not found for id: " + id + ". Cannot update"));
        return userRepository.save(new User(
                currUser.getId(),
                userDTO.getFirstName() != null ? userDTO.getFirstName() : currUser.getFirstName(),
                userDTO.getLastName() != null ? userDTO.getLastName() : currUser.getLastName(),
                userDTO.getEmail() != null ? userDTO.getEmail() : currUser.getEmail())
        );

    }

}
