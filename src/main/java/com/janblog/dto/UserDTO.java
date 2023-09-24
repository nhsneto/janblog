package com.janblog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.janblog.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.Instant;

public record UserDTO(
        String id,
        @NotNull(message = "{com.janblog.dto.UserDTO.username.notnull.msg}")
        @Size(min = 6, max = 30, message = "{com.janblog.dto.UserDTO.username.size.msg}")
        @Pattern(regexp = "^(?=.{6,30})[a-zA-Z][a-zA-Z0-9]*$", message = "{com.janblog.dto.UserDTO.username.pattern.msg}")
        String username,
        @NotNull(message = "{com.janblog.dto.UserDTO.email.notnull.msg}")
        @Email(regexp = "^(?=.{4,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-\\.][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*\\.[A-Za-z]{2,}$",
                message = "{com.janblog.dto.UserDTO.email.error.msg}")
        String email,
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @NotNull(message = "{com.janblog.dto.UserDTO.password.notnull.msg}")
        @Size(min = 8, max = 128, message = "{com.janblog.dto.UserDTO.password.size.msg}")
        @Pattern(regexp = "^[\\p{ASCII}]{8,128}$", message = "{com.janblog.dto.UserDTO.password.pattern.msg}")
        String password,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Role role,
        Instant createdAt,
        Instant updatedAt) {
}
