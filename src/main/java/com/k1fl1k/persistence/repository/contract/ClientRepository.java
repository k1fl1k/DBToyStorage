package com.k1fl1k.persistence.repository.contract;

import com.k1fl1k.persistence.entity.Client;
import com.k1fl1k.persistence.entity.Users.UsersRole;
import com.k1fl1k.persistence.repository.Repository;
import java.util.Optional;
import java.util.Set;

/**
 * Interface for managing Client entities.
 */
public interface ClientRepository extends Repository<Client> {

    /**
     * Finds a client by its name.
     *
     * @param name The name of the client to find.
     * @return An Optional containing the found client, or empty if not found.
     */
    Optional<Client> findByName(String name);

    /**
     * Finds clients by their user role.
     *
     * @param userRole The user role of the clients to find.
     * @return A set of clients with the specified user role.
     */
    Set<Client> findByUserRole(UsersRole userRole);
}
