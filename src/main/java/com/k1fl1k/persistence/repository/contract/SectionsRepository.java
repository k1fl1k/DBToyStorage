package com.k1fl1k.persistence.repository.contract;

import com.k1fl1k.persistence.entity.Sections;
import com.k1fl1k.persistence.repository.Repository;

import java.util.Optional;

/**
 * Repository interface for managing Sections entities.
 */
public interface SectionsRepository extends Repository<Sections>, OneToMany {

    /**
     * Find a section by its name.
     *
     * @param name The name of the section to find.
     * @return An Optional containing the found section, or empty if not found.
     */
    Optional<Sections> findByName(String name);

    /**
     * Find a section by its description.
     *
     * @param description The description of the section to find.
     * @return An Optional containing the found section, or empty if not found.
     */
    Optional<Sections> findByDescription(String description);
}
