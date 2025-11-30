package com.flightreservation.business.service;

import java.util.List;
import java.util.Objects;

import com.flightreservation.data.repository.Repository;
import com.flightreservation.model.User;

public class UserService {
    private final Repository<User> userRepository;

    public UserService(Repository<User> userRepository) {
        this.userRepository = Objects.requireNonNull(userRepository);
    }

    public List<User> getAllUsers() {
        return userRepository.getAll();
    }

    public User getUser(int id) {
        return userRepository.read(id);
    }

    public void createUser(User user) {
        userRepository.create(user);
    }

    public void updateUser(int id, User user) {
        userRepository.update(id, user);
    }

    public void deleteUser(int id) {
        userRepository.delete(id);
    }
}
