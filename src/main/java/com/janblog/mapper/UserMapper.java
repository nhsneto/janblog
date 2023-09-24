package com.janblog.mapper;

import com.janblog.dto.UserDTO;
import com.janblog.model.User;

public class UserMapper {

    public static User toUser(UserDTO dto) {
        return new User(dto.id(),
                dto.username(),
                dto.email(),
                dto.password(),
                dto.role(),
                dto.createdAt(),
                dto.updatedAt());
    }

    public static UserDTO toUserDTO(User user) {
        return new UserDTO(user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getRole(),
                user.getCreatedAt(),
                user.getUpdatedAt());
    }
}
