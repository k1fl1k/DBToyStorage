package com.k1fl1k.domain.exception;

/**
 * Exception thrown when a user is already authenticated but tries to authenticate again.
 */
public class UserAlreadyAuthenticatedException extends RuntimeException {

    /**
     * Constructs a UserAlreadyAuthenticatedException with the specified detail message.
     *
     * @param message The detail message.
     */
    public UserAlreadyAuthenticatedException(String message) {
        super(message);
    }
}
