package com.k1fl1k.persistence.repository.impl.jdbc;

import com.k1fl1k.persistence.entity.Users;
import com.k1fl1k.persistence.repository.GenericJdbcRepository;
import com.k1fl1k.persistence.repository.contract.TableNames;
import com.k1fl1k.persistence.repository.contract.UserRepository;
import com.k1fl1k.persistence.repository.mapper.impl.UserRowMapper;
import com.k1fl1k.persistence.util.ConnectionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

/**
 * JDBC implementation of the UserRepository interface.
 */
@Repository
public class UserRepositoryImpl extends GenericJdbcRepository<Users> implements UserRepository {

    /**
     * Constructs a new UserRepositoryImpl.
     *
     * @param connectionManager The ConnectionManager to be used for database connections.
     * @param userRowMapper     The UserRowMapper to be used for mapping ResultSet rows to Users objects.
     */
    public UserRepositoryImpl(ConnectionManager connectionManager, UserRowMapper userRowMapper) {
        super(connectionManager, userRowMapper, TableNames.USER.getName());
    }

    /**
     * Retrieves the attributes of the user table.
     *
     * @return A List of Strings representing the attributes of the user table.
     */
    @Override
    protected List<String> tableAttributes() {
        return List.of("login", "password", "role", "name");
    }

    /**
     * Retrieves the values of the user table for the specified user entity.
     *
     * @param user The Users object for which to retrieve the values.
     * @return A List of Objects representing the values of the user table for the specified user entity.
     */
    @Override
    protected List<Object> tableValues(Users user) {
        ArrayList<Object> values = new ArrayList<>();
        values.add(user.login());
        values.add(user.password());
        values.add(user.role().getName());
        values.add(user.name());
        return values;
    }

    /**
     * Finds a user by name.
     *
     * @param name The name of the user to find.
     * @return An Optional containing the Users object if found, or an empty Optional otherwise.
     */
    @Override
    public Optional<Users> findByName(String name) {
        return findBy("name", name);
    }

    /**
     * Finds a user by login.
     *
     * @param login The login of the user to find.
     * @return An Optional containing the Users object if found, or an empty Optional otherwise.
     */
    @Override
    public Optional<Users> findByLogin(String login) {
        return findBy("login", login);
    }
}
