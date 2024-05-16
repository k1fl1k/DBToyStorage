package com.k1fl1k.persistence.repository.contract;

import com.k1fl1k.persistence.entity.Manufacture;
import com.k1fl1k.persistence.repository.Repository;
import java.util.Optional;
import java.util.UUID;

/**
 * Interface for managing Manufacture entities.
 */
public interface ManufactureRepository extends Repository<Manufacture>, OneToMany {

    /**
     * Finds a manufacture by its name.
     *
     * @param name The name of the manufacture to find.
     * @return An Optional containing the found manufacture, or empty if not found.
     */
    Optional<Manufacture> findByName(String name);
}
