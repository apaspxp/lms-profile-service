package com.lms.profileservice.exceptions;

public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException(String errorMessage) {
        super(errorMessage);
    }
}