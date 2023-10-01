package com.janblog.service;

import com.janblog.dto.EmailDTO;
import com.janblog.dto.PasswordDTO;
import com.janblog.dto.UserDTO;
import com.janblog.exception.EmailException;
import com.janblog.exception.PasswordException;
import com.janblog.exception.UserException;
import com.janblog.mapper.UserMapper;
import com.janblog.model.Role;
import com.janblog.model.User;
import com.janblog.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
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
                .orElseThrow(() -> new UserException("User with id=" + id + " was not found"));
        return UserMapper.toUserDTO(user);
    }

    public UserDTO save(@Valid UserDTO dto) {
        Objects.requireNonNull(dto.password(), "Password must not be null");
        User user = UserMapper.toUser(dto);
        user.setRole(Role.usr);
        user.setCreatedAt(Instant.now());
        user.setUpdatedAt(Instant.now());
        return UserMapper.toUserDTO(userRepo.save(user));
    }

    public void changePassword(String id, @Valid PasswordDTO dto) {
        User user = UserMapper.toUser(findById(id));
        if (!user.getPassword().equals(dto.oldPassword())) {
            throw new PasswordException("Old Password does not match");
        }
        user.setPassword(dto.newPassword());
        user.setUpdatedAt(Instant.now());
        userRepo.save(user);
    }

    public void changeEmail(String id, @Valid EmailDTO dto) {
        User user = UserMapper.toUser(findById(id));
        if (!user.getEmail().equalsIgnoreCase(dto.oldEmail())) {
            throw new EmailException("Old email does not match");
        }
        user.setEmail(dto.newEmail().toLowerCase());
        user.setUpdatedAt(Instant.now());
        userRepo.save(user);
    }

    public void deleteById(String id) {
        findById(id);
        userRepo.deleteById(id);
    }
}
