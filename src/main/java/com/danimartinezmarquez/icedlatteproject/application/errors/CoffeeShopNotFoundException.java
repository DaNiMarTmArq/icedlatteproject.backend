package com.danimartinezmarquez.icedlatteproject.application.errors;

public class CoffeeShopNotFoundException extends RuntimeException {
    public CoffeeShopNotFoundException(String message) {
        super(message);
    }

    public CoffeeShopNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
