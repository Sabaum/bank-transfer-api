package com.revolut.banktransferapi.exception;

public class OutOfFundsException extends ValidationException {

    public OutOfFundsException(String message) {
        super(message);
    }
}
