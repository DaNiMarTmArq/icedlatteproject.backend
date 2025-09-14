package com.danimartinezmarquez.icedlatteproject.api.utils;

import java.util.NoSuchElementException;
import java.util.Optional;

public class Result<T> {
    private final T value;

    private Result(T value) {
        this.value = value;
    }

    public static <T> Result<T> of(T value) {
        return new Result<>(value);
    }

    public static <T> Result<T> empty() {
        return new Result<>(null);
    }

    public boolean isPresent() {
        return value != null;
    }

    public boolean isEmpty() {
        return value == null;
    }

    public T get() {
        if (value == null) {
            throw new NoSuchElementException("No value present in Result");
        }
        return value;
    }

    public Optional<T> toOptional() {
        return Optional.ofNullable(value);
    }
}