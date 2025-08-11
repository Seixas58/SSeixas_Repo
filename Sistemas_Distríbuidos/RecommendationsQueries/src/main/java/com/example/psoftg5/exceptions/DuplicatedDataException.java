package com.example.psoftg5.exceptions;

public class DuplicatedDataException extends RuntimeException {
    public DuplicatedDataException(Class<?> entityClass, String value) {
        super(entityClass.getSimpleName() + " with value '" + value + "' already exists.");
    }
}
