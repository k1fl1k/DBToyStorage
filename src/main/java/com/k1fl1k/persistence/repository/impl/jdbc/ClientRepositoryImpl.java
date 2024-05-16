package com.k1fl1k.persistence.repository.impl.jdbc;

import com.k1fl1k.persistence.entity.Client;
import com.k1fl1k.persistence.entity.Users.UsersRole;
import com.k1fl1k.persistence.exception.EntityNotFoundException;
import com.k1fl1k.persistence.repository.GenericJdbcRepository;
import com.k1fl1k.persistence.repository.contract.ClientRepository;
import com.k1fl1k.persistence.repository.contract.TableNames;
import com.k1fl1k.persistence.repository.mapper.impl.ClientRowMapper;
import com.k1fl1k.persistence.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Repository;

/**
 * Implementation of the ClientRepository interface using JDBC.
 */
@Repository
public class ClientRepositoryImpl extends GenericJdbcRepository<Client> implements ClientRepository {

    private final ConnectionManager connectionManager;
    private final ClientRowMapper clientRowMapper;
    private final JdbcManyToMany<Client> jdbcManyToMany;

    /**
     * Constructs a new ClientRepositoryImpl instance.
     *
     * @param connectionManager The ConnectionManager used to manage database connections.
     * @param clientRowMapper   The ClientRowMapper used to map ResultSet rows to Client entities.
     * @param jdbcManyToMany    The JdbcManyToMany utility class for handling many-to-many relationships.
     */
    public ClientRepositoryImpl(
        ConnectionManager connectionManager,
        ClientRowMapper clientRowMapper,
        JdbcManyToMany<Client> jdbcManyToMany) {
        super(connectionManager, clientRowMapper, TableNames.CLIENT.getName());
        this.connectionManager = connectionManager;
        this.clientRowMapper = clientRowMapper;
        this.jdbcManyToMany = jdbcManyToMany;
    }

    @Override
    protected List<String> tableAttributes() {
        return List.of(
            "name",
            "phone",
            "address",
            "section_id"
        );
    }

    @Override
    protected List<Object> tableValues(Client client) {
        return List.of(
            client.name(),
            client.phone(),
            client.address()
        );
    }

    @Override
    public Optional<Client> findByName(String name) {
        return findBy("name", name);
    }

    @Override
    public Set<Client> findByUserRole(UsersRole userRole) {
        final String selectUserIdSql = "SELECT id FROM users WHERE role = ?";
        final String selectClientSql = "SELECT * FROM client WHERE id = ?";
        UUID clientId = null;

        try (Connection connection = connectionManager.get();
            PreparedStatement selectUserIdStatement = connection.prepareStatement(selectUserIdSql)) {

            selectUserIdStatement.setString(1, userRole.toString());
            ResultSet resultSet = selectUserIdStatement.executeQuery();

            if (resultSet.next()) {
                clientId = UUID.fromString(resultSet.getString("id"));
            }

        } catch (SQLException throwables) {
            throw new EntityNotFoundException("Error searching for client");
        }

        if (clientId != null) {
            return jdbcManyToMany.getByPivot(
                clientId,
                selectClientSql,
                clientRowMapper,
                "Error searching for client: " + userRole
            );
        } else {
            return Set.of();
        }
    }
}
