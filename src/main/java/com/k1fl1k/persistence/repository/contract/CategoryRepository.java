package com.k1fl1k.persistence.repository.contract;

import com.k1fl1k.persistence.entity.Category;
import com.k1fl1k.persistence.repository.Repository;
import java.util.Set;
import java.util.UUID;

/**
 * Interface for managing Category entities.
 */
public interface CategoryRepository extends Repository<Category>, OneToMany {

    /**
     * Finds all categories associated with a specific toy ID.
     *
     * @param toyId The ID of the toy.
     * @return A set of categories associated with the specified toy ID.
     */
    Set<Category> findAllByToyId(UUID toyId);
}
