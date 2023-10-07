package com.janblog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.janblog.model.Role;
import com.janblog.validation.Password;
import com.janblog.validation.Username;
import com.janblog.validation.ValidEmail;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record UserDTO(
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        String id,
        @NotNull(message = "Username is required")
        @Username
        String username,
        @NotNull(message = "Email is required")
        @ValidEmail
        String email,
        @NotNull(message = "Password is required")
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @Password
        String password,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Role role,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Instant createdAt,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Instant updatedAt) {
}
