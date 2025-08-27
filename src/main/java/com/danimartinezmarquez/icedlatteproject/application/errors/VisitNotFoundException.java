package com.danimartinezmarquez.icedlatteproject.application.errors;

public class VisitNotFoundException extends RuntimeException {
    public VisitNotFoundException(String message) {
        super(message);
    }
}
