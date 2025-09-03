package com.danimartinezmarquez.icedlatteproject.api.exceptions;

public class FileExtensionNotValidException extends RuntimeException {
    public FileExtensionNotValidException(String message) {
        super(message);
    }
}
