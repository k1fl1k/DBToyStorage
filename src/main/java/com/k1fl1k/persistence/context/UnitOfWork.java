package com.k1fl1k.persistence.context;

import com.k1fl1k.persistence.entity.Entity;
import java.util.Set;
import java.util.UUID;

/**
 * Interface for managing a unit of work, which tracks changes to entities and allows for committing those changes.
 *
 * @param <T> The type of entity managed by this unit of work.
 */
public interface UnitOfWork<T extends Entity> {

    String INSERT = "INSERT";
    String DELETE = "DELETE";
    String MODIFY = "MODIFY";

    /**
     * Registers a new entity to be added.
     *
     * @param entity The new entity to be added.
     */
    void registerNew(T entity);

    /**
     * Registers a modified entity.
     *
     * @param entity The modified entity.
     */
    void registerModified(T entity);

    /**
     * Registers an entity to be deleted.
     *
     * @param entity The entity to be deleted.
     */
    void registerDeleted(T entity);

    /**
     * Registers an entity with the given ID to be deleted.
     *
     * @param id The ID of the entity to be deleted.
     */
    void registerDeleted(UUID id);

    /**
     * Commits the changes tracked by this unit of work.
     */
    void commit();

    /**
     * Retrieves the entity with the specified ID.
     *
     * @param id The ID of the entity to retrieve.
     * @return The entity with the specified ID, or null if not found.
     */
    T getEntity(UUID id);

    /**
     * Retrieves the current entity being managed by this unit of work.
     *
     * @return The current entity being managed.
     */
    T getEntity();

    /**
     * Retrieves all entities managed by this unit of work.
     *
     * @return A set of all entities managed.
     */
    Set<T> getEntities();
}
