package com.danimartinezmarquez.icedlatteproject.api.exceptions;

public class UserInvalidCredentialsException extends RuntimeException {
    public UserInvalidCredentialsException(String message) {
        super(message);
    }
}