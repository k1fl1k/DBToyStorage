package com.k1fl1k.persistence.repository.contract;

import com.k1fl1k.persistence.entity.Sections;
import com.k1fl1k.persistence.repository.Repository;
import java.util.Optional;

public interface SectionsRepository extends Repository<Sections>, OneToMany {

    Optional<Sections> findByName(String name);

    Optional<Sections> findByDescription(String description);
}
