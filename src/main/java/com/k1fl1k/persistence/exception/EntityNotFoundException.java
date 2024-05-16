package com.k1fl1k.persistence.exception;

import java.io.Serial;
/**
 * Exception thrown when an entity is not found.
 */
public class EntityNotFoundException extends RuntimeException {

    @Serial private static final long serialVersionUID = -6486296592874470767L;
    /**
     * Constructs a new EntityNotFoundException with the specified error message.
     *
     * @param message The error message.
     */
    public EntityNotFoundException(String message) {
        super(message);
    }
}