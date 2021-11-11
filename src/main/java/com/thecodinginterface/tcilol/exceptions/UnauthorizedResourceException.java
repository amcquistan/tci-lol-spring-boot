package com.thecodinginterface.tcilol.exceptions;

public class UnauthorizedResourceException extends RuntimeException {
    public UnauthorizedResourceException() {
        super();
    }

    public UnauthorizedResourceException(String message) {
        super(message);
    }
}
