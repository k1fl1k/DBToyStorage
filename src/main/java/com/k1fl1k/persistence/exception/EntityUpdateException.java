package com.k1fl1k.persistence.exception;

/**
 * Exception thrown when an error occurs during an entity update operation.
 */
public class EntityUpdateException extends RuntimeException {

    /**
     * Constructs a new EntityUpdateException with the specified error message.
     *
     * @param message The error message.
     */
    public EntityUpdateException(String message) {
        super(message);
    }
}
