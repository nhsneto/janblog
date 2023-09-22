package com.janblog.service;

import com.janblog.model.Role;
import com.janblog.model.User;
import com.janblog.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public User findById(String id) {
        Optional<User> opt = userRepo.findById(id);
        return opt.orElseThrow(() -> new NoSuchElementException("User with id=" + id + " was not found."));
    }

    public User save(User user) {
        user.setRole(Role.usr);
        user.setCreatedAt(Instant.now());
        user.setUpdatedAt(Instant.now());
        return userRepo.save(user);
    }

    public User update(String id, User updated) {
        User user = findById(id);
        user.setUsername(updated.getUsername());
        user.setPassword(updated.getPassword());
        user.setRole(Role.usr);
        user.setUpdatedAt(Instant.now());
        return userRepo.save(user);
    }

    public void deleteById(String id) {
        userRepo.deleteById(id);
    }
}
