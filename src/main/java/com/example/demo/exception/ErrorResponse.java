package com.example.demo.exception;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


public class ErrorResponse {
    private LocalDateTime timestamp;
    private String message;
    private Integer status;

    public ErrorResponse(LocalDateTime timestamp, String message, Integer status) {
        this.timestamp = timestamp;
        this.message = message;
        this.status = status;
    }

    public ErrorResponse() {
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
}
