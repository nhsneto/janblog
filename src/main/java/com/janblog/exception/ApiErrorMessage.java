package com.janblog.exception;

import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

public class ApiErrorMessage {

    private final Instant timestamp;
    private final String title;
    private final int status;
    private final List<String> errors;

    public ApiErrorMessage(HttpStatus status, List<String> messages) {
        this.timestamp = Instant.now();
        this.title = status.getReasonPhrase();
        this.status = status.value();
        this.errors = messages;
    }

    public ApiErrorMessage(HttpStatus status, String message) {
        this.timestamp = Instant.now();
        this.title = status.getReasonPhrase();
        this.status = status.value();
        this.errors = Collections.singletonList(message);
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public String getTitle() {
        return title;
    }

    public int getStatus() {
        return status;
    }

    public List<String> getErrors() {
        return errors;
    }
}
