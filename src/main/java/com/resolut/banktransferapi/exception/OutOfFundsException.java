package com.resolut.banktransferapi.exception;

public class OutOfFundsException extends RuntimeException {

    public OutOfFundsException(String message) {
        super(message);
    }
}
