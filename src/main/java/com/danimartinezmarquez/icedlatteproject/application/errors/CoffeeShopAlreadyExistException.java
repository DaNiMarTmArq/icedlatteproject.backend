package com.danimartinezmarquez.icedlatteproject.application.errors;

public class CoffeeShopAlreadyExistException extends RuntimeException {
    public CoffeeShopAlreadyExistException(String message) {
        super(message);
    }
}
