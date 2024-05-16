package com.k1fl1k.persistence.repository;

import com.k1fl1k.persistence.entity.Entity;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * Generic interface for a repository that provides basic CRUD operations for entities.
 * @param <T> The type of entity managed by the repository, extending Entity
 */
public interface Repository<T extends Entity> {

    /**
     * Finds an entity by its ID.
     * @param id The ID of the entity to find
     * @return An Optional containing the found entity, or empty if not found
     */
    Optional<T> findById(UUID id);

    /**
     * Finds an entity by a specific column value.
     * @param column The column name to search by
     * @param value The value to search for in the specified column
     * @return An Optional containing the found entity, or empty if not found
     */
    Optional<T> findBy(String column, Object value);

    /**
     * Finds an entity by login.
     * @param column The column name to search by (typically login-related)
     * @param value The login value to search for
     * @return An Optional containing the found entity, or empty if not found
     */
    Optional<T> findByLogin(String column, Object value);

    /**
     * Retrieves all entities from the repository.
     * @return A Set containing all entities
     */
    Set<T> findAll();

    /**
     * Retrieves all entities that meet a specific criteria defined by a where query.
     * @param whereQuery The where query specifying the criteria
     * @return A Set containing all entities that meet the criteria
     */
    Set<T> findAllWhere(String whereQuery);

    /**
     * Saves or updates an entity in the repository.
     * @param entity The entity to save or update
     * @return The saved or updated entity
     */
    T save(T entity);

    /**
     * Saves or updates a collection of entities in the repository.
     * @param entities The collection of entities to save or update
     * @return A Set containing the saved or updated entities
     */
    Set<T> save(Collection<T> entities);

    /**
     * Deletes an entity from the repository by its ID.
     * @param id The ID of the entity to delete
     * @return True if the deletion was successful, false otherwise
     */
    boolean delete(UUID id);

    /**
     * Deletes a collection of entities from the repository by their IDs.
     * @param ids The collection of IDs of the entities to delete
     * @return True if all deletions were successful, false otherwise
     */
    boolean delete(Collection<UUID> ids);
}
