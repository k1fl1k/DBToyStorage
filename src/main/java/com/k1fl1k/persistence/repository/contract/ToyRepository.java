package com.k1fl1k.persistence.repository.contract;

import com.k1fl1k.persistence.entity.Toy;
import com.k1fl1k.persistence.repository.Repository;
import java.util.Set;
import java.util.UUID;

public interface ToyRepository extends Repository<Toy>, ManyToMany {

    Set<Toy> findAllToysCategory(UUID categoryId);

}
