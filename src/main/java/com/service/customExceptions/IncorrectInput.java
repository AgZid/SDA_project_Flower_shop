package com.service.customExceptions;

public class IncorrectInput extends Exception {

    public IncorrectInput(String errorMessage) {
        super(errorMessage);
    }

}
