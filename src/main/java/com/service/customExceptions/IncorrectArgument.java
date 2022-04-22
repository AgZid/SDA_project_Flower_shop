package com.service.customExceptions;

public class IncorrectArgument extends RuntimeException{
    public IncorrectArgument(String message) {
        super(message);
    }
}
