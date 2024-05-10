package com.k1fl1k.persistence.repository.contract;

import com.k1fl1k.persistence.entity.Client;
import com.k1fl1k.persistence.entity.Users.UsersRole;
import com.k1fl1k.persistence.repository.Repository;
import java.util.Optional;
import java.util.Set;

public interface ClientRepository extends Repository<Client> {
    Optional<Client> findByName(String name);
    Set<Client> findByUserRole(UsersRole UsersRole);
}
