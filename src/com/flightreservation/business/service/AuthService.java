package com.flightreservation.business.service;

import java.util.Objects;

import com.flightreservation.data.repository.Repository;
import com.flightreservation.model.Role;
import com.flightreservation.model.User;

public class AuthService {
    private final Repository<User> userRepository;

    public AuthService(Repository<User> userRepository) {
        this.userRepository = Objects.requireNonNull(userRepository);
    }

    public User login(String email, String password) {
        if (email == null || password == null) {
            return null;
        }

        return userRepository.getAll().stream()
                .filter(u -> email.equalsIgnoreCase(u.getEmail()))
                .filter(u -> password.equals(u.getPassword()))
                .findFirst()
                .orElse(null);
    }


    public boolean verifyRole(User user, Role requiredRole) {
        if (user == null || requiredRole == null) {
            return false;
        }
        return requiredRole.equals(user.getRole());
    }
}
