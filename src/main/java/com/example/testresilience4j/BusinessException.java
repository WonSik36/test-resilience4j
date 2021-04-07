package com.example.testresilience4j;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
