package com.eric.restapi.user_service.repository;

import com.eric.restapi.user_service.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    private final List<User> users;
    private Long CURR_USER_ID = 100L;

    public UserRepository() {
        users = new ArrayList<>();
        users.add(new User(CURR_USER_ID++, "Eric", "Macio", "eric.macio@gmail.com"));
        users.add(new User(CURR_USER_ID++, "patou", "Macio", "patou.macio@gmail.com"));
        users.add(new User(CURR_USER_ID++, "Bob", "Berry", "bob.berry@gmail.com"));
    }

    public List<User> getUsers() {
        return users;
    }

    public Optional<User> getUser(String id) {
        return users.stream()
                .filter(u -> u.getId().equals(Long.valueOf(id)))
                .findAny();
    }

    public Optional<User> deleteUser(String id) {
        Iterator<User> iter = users.iterator();
        User deletedUser = null;
        while(iter.hasNext()) {
            User user = iter.next();
            if(user.getId().equals(Long.valueOf(id))) {
                deletedUser = user;
                iter.remove();
                break;
            }
        }
        return Optional.ofNullable(deletedUser);
    }

    public User patchUser(String id, User updatedUser) {
        User currUser = users.stream()
                .filter(u -> u.getId().equals(Long.valueOf(id)))
                .findAny().get();
        users.set(users.indexOf(currUser), updatedUser);
        return updatedUser;
    }

    public User saveUser(User user) {
        User newUser = new User(
                CURR_USER_ID++,
                user.getFirstName(),
                user.getLastName(),
                user.getEmail()
        );
        users.add(newUser);
        return newUser;
    }
}
