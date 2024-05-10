package com.k1fl1k.domain.exception;

public class UserAlreadyAuthenticatedException extends RuntimeException {

    public UserAlreadyAuthenticatedException(String message) {
        super(message);
    }
}

