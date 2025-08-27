package com.danimartinezmarquez.icedlatteproject.application.errors;

public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException(String message) {
        super(message);
    }
}
