package com.resolut.banktransferapi.exception;

public class InvalidOperationException extends ValidationException {

    public InvalidOperationException(String message) {
        super(message);
    }
}
