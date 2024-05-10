package com.k1fl1k.persistence.repository.contract;

import com.k1fl1k.persistence.entity.Category;
import com.k1fl1k.persistence.repository.Repository;
import java.util.Set;
import java.util.UUID;

public interface CategoryRepository extends Repository<Category>, OneToMany {

    Set<Category> findAllByToyId(UUID toyId);
}
