package com.janblog.dto;

import com.janblog.validation.ValidEmail;
import jakarta.validation.constraints.NotNull;

public record EmailDTO(
        @NotNull(message = "oldEmail is required")
        @ValidEmail
        String oldEmail,
        @NotNull(message = "newEmail is required")
        @ValidEmail
        String newEmail) {
}
