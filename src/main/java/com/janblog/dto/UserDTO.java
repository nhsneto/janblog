package com.janblog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.janblog.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.Instant;

public record UserDTO(
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        String id,
        @NotNull(message = "Username must not be null")
        @Size(min = 6, max = 30, message = "Username must be between 6 and 30 characters long")
        @Pattern(regexp = "^(?=.{6,30})[a-zA-Z][a-zA-Z0-9]*$", message = "Username must be alphanumeric and start with a letter")
        String username,
        @NotNull(message = "Email must not be null")
        @Email(regexp = "^(?=.{4,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-\\.][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*\\.[A-Za-z]{2,}$",
                message = "Invalid email address. Email should be in someone@example.com format")
        String email,
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @Size(min = 8, max = 128, message = "Password must be between 8 and 128 characters long")
        @Pattern(regexp = "^[\\p{ASCII}]{8,128}$", message = "Password must have ASCII characters only")
        String password,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Role role,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Instant createdAt,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Instant updatedAt) {
}
