package com.k1fl1k.persistence.repository.contract;

import com.k1fl1k.persistence.entity.Manufacture;
import com.k1fl1k.persistence.repository.Repository;
import java.util.Optional;

public interface ManufactureRepository extends Repository<Manufacture>, OneToMany {

    Optional<Manufacture> findByName(String name);
}
