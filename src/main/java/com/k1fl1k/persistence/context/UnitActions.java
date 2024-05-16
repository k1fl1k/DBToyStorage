package com.k1fl1k.persistence.context;

/**
 * Enum representing the types of actions that can be performed within a unit of work.
 */
public enum UnitActions {
    /** Represents an insert operation within a unit of work. */
    INSERT,

    /** Represents a delete operation within a unit of work. */
    DELETE,

    /** Represents a modify operation within a unit of work. */
    MODIFY
}
