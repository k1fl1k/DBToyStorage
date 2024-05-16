package com.k1fl1k.persistence.repository.contract;

import com.k1fl1k.persistence.entity.Toy;
import com.k1fl1k.persistence.repository.Repository;

import java.util.Set;
import java.util.UUID;

/**
 * Repository contract for accessing toy data.
 */
public interface ToyRepository extends Repository<Toy>, ManyToMany {

    /**
     * Finds all toys belonging to a specific category.
     *
     * @param categoryId The ID of the category to which the toys belong.
     * @return A set of toys belonging to the specified category.
     */
    Set<Toy> findAllToysCategory(UUID categoryId);
}
