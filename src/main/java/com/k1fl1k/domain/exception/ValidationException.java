package com.k1fl1k.domain.exception;

import jakarta.validation.ConstraintViolation;
import java.io.Serial;
import java.util.Set;
/**
 * Exception class representing validation errors during data validation.
 */
public class ValidationException extends RuntimeException {

    private static final long serialVersionUID = 3145152535586258949L;
    private final Set<? extends ConstraintViolation<?>> violations;

    /**
     * Constructs a ValidationException with the specified message and set of constraint violations.
     *
     * @param message    The detail message.
     * @param violations The set of constraint violations.
     */
    public ValidationException(String message, Set<? extends ConstraintViolation<?>> violations) {
        super(message);
        this.violations = violations;
    }

    /**
     * Retrieves the set of constraint violations associated with this exception.
     *
     * @return The set of constraint violations.
     */
    public Set<? extends ConstraintViolation<?>> getViolations() {
        return violations;
    }

    /**
     * Creates a new ValidationException with the specified suffix and set of constraint violations.
     *
     * @param suffix     The suffix describing the validation operation.
     * @param violations The set of constraint violations.
     * @return A new ValidationException instance.
     */
    public static ValidationException create(String suffix, Set<? extends ConstraintViolation<?>> violations) {
        return new ValidationException("Помилка валідації при" + suffix, violations);
    }
}
