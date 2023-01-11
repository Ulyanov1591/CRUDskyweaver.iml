package com.godov.crudskyweaver.dto;

import java.time.LocalDateTime;

public class ErrorDTO {
    private final LocalDateTime timestamp;
    private final String message;

    public ErrorDTO(LocalDateTime timestamp, String message) {
        this.timestamp = timestamp;
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

}
