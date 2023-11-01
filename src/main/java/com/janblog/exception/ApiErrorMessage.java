package com.janblog.exception;

import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

public class ApiErrorMessage {

    private final Instant timestamp;
    private final String status;
    private final List<String> errors;

    public ApiErrorMessage(HttpStatus status, List<String> messages) {
        this.timestamp = Instant.now();
        this.status = status.toString().replace('_', ' ').toLowerCase();
        this.errors = messages;
    }

    public ApiErrorMessage(HttpStatus status, String message) {
        this.timestamp = Instant.now();
        this.status = status.toString().replace('_', ' ').toLowerCase();
        this.errors = Collections.singletonList(message);
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public String getStatus() {
        return status;
    }

    public List<String> getErrors() {
        return errors;
    }
}
