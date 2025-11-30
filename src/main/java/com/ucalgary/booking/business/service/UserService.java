package com.ucalgary.booking.business.service;

import java.util.List;
import java.util.Objects;

import com.ucalgary.booking.User;
import com.ucalgary.booking.UserRepository;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = Objects.requireNonNull(userRepository);
    }

    public List<User> getAllUsers() {
        return userRepository.readAll();
    }

    public void createUser(User user) {
        userRepository.create(user);
    }

    public void updateUser(long id, User user) {
        userRepository.update(id, user);
    }
}
