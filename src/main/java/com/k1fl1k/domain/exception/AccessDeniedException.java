package com.k1fl1k.domain.exception;

import java.io.Serial;

public class AccessDeniedException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 174948262083496647L;
    /**
     * Constructs an AccessDeniedException with the specified error message.
     *
     * @param message The detail message (which is saved for later retrieval by the getMessage() method)
     */
    public AccessDeniedException(String message) {
        super(message);
    }
    /**
     * Creates an AccessDeniedException indicating that the user is not the author of the resource.
     *
     * @param suffix Additional information about the denied access.
     * @return An AccessDeniedException instance.
     */
    public static AccessDeniedException notAuthorUser(String suffix) {
        return new AccessDeniedException(STR."Ви не є автором, тому не маєте права \{suffix}.");
    }
}
