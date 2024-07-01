package com.eric.restapi.user_service;

import com.eric.restapi.user_service.controller.UserDTO;
import com.eric.restapi.user_service.exceptions.NotFoundException;
import com.eric.restapi.user_service.model.User;
import com.eric.restapi.user_service.repository.UserRepository;
import com.eric.restapi.user_service.service.UserService;
import com.eric.restapi.user_service.service.UserServiceImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTests {

    @MockBean
    private UserRepository userRepositoryMock;

    @Autowired
    private UserService userService;

    @DisplayName("Get user by email")
    @Test
    void getUserByEmail() {
        String expectedFirstName = "arthur";
        String expectedLastName = "Corato";
        String expectedEmail = "arthur.corato@gmail.com";
        Long expectedId = 99999L;
        User expectedUser = new User(expectedId, expectedFirstName, expectedLastName, expectedEmail);
        when(userRepositoryMock.findUserByEmail( anyString() )).thenReturn(Optional.of(expectedUser));
        User user = userService.getUserByEmail("eric@gmail.com");
        System.out.println("user: " + user);
        assertNotNull(user);
        assertEquals(expectedId, user.getId());
        assertEquals(expectedFirstName, user.getFirstName());
        assertEquals(expectedLastName, user.getLastName());
        assertEquals(expectedEmail, user.getEmail());
    }

    @DisplayName("Get user by id")
    @Test
    void getUserById() {
        String expectedFirstName = "arthur";
        String expectedLastName = "Corato";
        String expectedEmail = "arthur.corato@gmail.com";
        Long expectedId = 99999L;
        User expectedUser = new User(expectedId, expectedFirstName, expectedLastName, expectedEmail);
        when(userRepositoryMock.findUserById(expectedId)).thenReturn(Optional.of(expectedUser));
        User user = userService.getUser(expectedId);
        System.out.println("user: " + user);
        assertNotNull(user);
        assertEquals(expectedId, user.getId());
        assertEquals(expectedFirstName, user.getFirstName());
        assertEquals(expectedLastName, user.getLastName());
        assertEquals(expectedEmail, user.getEmail());
    }

    @DisplayName("Get user exception")
    @Test
    void getUserException() {
        when(userRepositoryMock.findUserById( anyLong() )).thenReturn(Optional.ofNullable(null));
        assertThrows(NotFoundException.class, () -> userService.getUser(352L));
    }
}
