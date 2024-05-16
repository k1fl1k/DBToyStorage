package com.k1fl1k.persistence.repository.contract;

import com.k1fl1k.persistence.entity.Users;
import com.k1fl1k.persistence.repository.Repository;

import java.util.Optional;

/**
 * Repository contract for accessing user data.
 */
public interface UserRepository extends Repository<Users> {

    /**
     * Finds a user by their login.
     *
     * @param login The login of the user to find.
     * @return An Optional containing the user if found, or empty otherwise.
     */
    Optional<Users> findByLogin(String login);

    /**
     * Finds a user by their name.
     *
     * @param name The name of the user to find.
     * @return An Optional containing the user if found, or empty otherwise.
     */
    Optional<Users> findByName(String name);
}
