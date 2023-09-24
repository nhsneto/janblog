package com.janblog.mapper;

import com.janblog.dto.UserDTO;
import com.janblog.model.User;

import java.util.List;
import java.util.stream.Collectors;

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

    public static List<UserDTO> toUserDTOList(List<User> userList) {
        return userList.stream()
                .map(UserMapper::toUserDTO)
                .collect(Collectors.toList());
    }

    public static List<User> toUserList(List<UserDTO> userDTOList) {
        return userDTOList.stream()
                .map(UserMapper::toUser)
                .collect(Collectors.toList());
    }
}
