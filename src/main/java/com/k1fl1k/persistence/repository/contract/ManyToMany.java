package com.k1fl1k.persistence.repository.contract;

import java.util.UUID;

/**
 * Interface for managing many-to-many relationships.
 */
public interface ManyToMany {

    /**
     * Attaches two entities in a many-to-many relationship.
     *
     * @param firstId  The ID of the first entity.
     * @param secondId The ID of the second entity.
     * @return true if the attachment was successful, false otherwise.
     */
    boolean attach(UUID firstId, UUID secondId);

    /**
     * Detaches two entities in a many-to-many relationship.
     *
     * @param firstId  The ID of the first entity.
     * @param secondId The ID of the second entity.
     * @return true if the detachment was successful, false otherwise.
     */
    boolean detach(UUID firstId, UUID secondId);
}
