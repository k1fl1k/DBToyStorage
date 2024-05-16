package com.k1fl1k.domain.exception;

/**
 * Exception thrown when authentication fails due to incorrect login or password.
 */
public class AuthenticationException extends RuntimeException {

    /**
     * Constructs an AuthenticationException with a default error message.
     */
    public AuthenticationException() {
        super("Не вірний логін чи пароль.");
    }
}
