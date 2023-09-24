package com.janblog.service;

import com.janblog.dto.UserDTO;
import com.janblog.mapper.UserMapper;
import com.janblog.model.Role;
import com.janblog.model.User;
import com.janblog.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class UserService {

    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public List<UserDTO> findAll() {
        return UserMapper.toUserDTOList(userRepo.findAll());
    }

    public UserDTO findById(String id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User with id=" + id + " was not found."));
        return UserMapper.toUserDTO(user);
    }

    public UserDTO save(UserDTO dto) {
        User user = UserMapper.toUser(dto);
        user.setRole(Role.usr);
        user.setCreatedAt(Instant.now());
        user.setUpdatedAt(Instant.now());
        return UserMapper.toUserDTO(userRepo.save(user));
    }

    public UserDTO update(String id, UserDTO updated) {
        User user = UserMapper.toUser(findById(id));
        user.setUsername(updated.username());
        user.setEmail(updated.email());
        user.setUpdatedAt(Instant.now());
        return UserMapper.toUserDTO(userRepo.save(user));
    }

    public void deleteById(String id) {
        findById(id);
        userRepo.deleteById(id);
    }
}
