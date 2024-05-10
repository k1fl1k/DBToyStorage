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

@Repository
public class UserRepositoryImpl extends GenericJdbcRepository<Users> implements UserRepository {

    public UserRepositoryImpl(ConnectionManager connectionManager, UserRowMapper userRowMapper) {
        super(connectionManager, userRowMapper, TableNames.USER.getName());
    }

    @Override
    protected List<String> tableAttributes() {
        return List.of("login", "password", "role", "name");
    }

    @Override
    protected List<Object> tableValues(Users user) {
        ArrayList<Object> values = new ArrayList<>();
        values.add(user.login());
        values.add(user.password());
        values.add(user.role().getName());
        values.add(user.name());
        return values;
    }

    @Override
    public Optional<Users> findByName(String name) {
        return findBy("name", name);
    }

    @Override
    public Optional<Users> findByLogin(String login) {
        return findBy("login", login);
    }
}