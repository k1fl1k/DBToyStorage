package com.k1fl1k.persistence.exception;

/**
 * Exception thrown when an error occurs during entity deletion.
 */
public class EntityDeleteException extends RuntimeException {

    /**
     * Constructs a new EntityDeleteException with the specified error message.
     *
     * @param message The error message.
     */
    public EntityDeleteException(String message) {
        super(message);
    }
}
