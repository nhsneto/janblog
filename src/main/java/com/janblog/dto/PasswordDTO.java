package com.janblog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.janblog.validation.Password;
import jakarta.validation.constraints.NotNull;

public record PasswordDTO(
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @NotNull(message = "oldPassword is required")
        @Password
        String oldPassword,
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @NotNull(message = "newPassword is required")
        @Password
        String newPassword) {
}
