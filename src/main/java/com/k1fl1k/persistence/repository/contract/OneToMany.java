package com.k1fl1k.persistence.repository.contract;

import java.util.UUID;

/**
 * Interface for managing one-to-many relationships.
 */
public interface OneToMany {

    /**
     * Attaches a child entity to a parent entity in a one-to-many relationship.
     *
     * @param parentId The ID of the parent entity.
     * @param childId  The ID of the child entity.
     * @return true if the attachment was successful, false otherwise.
     */
    boolean attach(UUID parentId, UUID childId);

    /**
     * Detaches a child entity from a parent entity in a one-to-many relationship.
     *
     * @param parentId The ID of the parent entity.
     * @param childId  The ID of the child entity.
     * @return true if the detachment was successful, false otherwise.
     */
    boolean detach(UUID parentId, UUID childId);
}
