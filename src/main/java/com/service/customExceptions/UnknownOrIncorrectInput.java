package com.service.customExceptions;

public class UnknownOrIncorrectInput extends Exception  {

        public UnknownOrIncorrectInput(String errorMessage) {
            super(errorMessage);
        }
}
