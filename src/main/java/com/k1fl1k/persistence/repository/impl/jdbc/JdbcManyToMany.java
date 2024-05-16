package com.k1fl1k.persistence.repository.impl.jdbc;

import com.k1fl1k.persistence.exception.EntityDeleteException;
import com.k1fl1k.persistence.exception.EntityNotFoundException;
import com.k1fl1k.persistence.repository.mapper.RowMapper;
import com.k1fl1k.persistence.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Component;

/**
 * Utility class for executing many-to-many database operations using JDBC.
 *
 * @param <T> The type of entity handled by this class.
 */
@Component
public class JdbcManyToMany<T> {
    private final ConnectionManager connectionManager;

    /**
     * Constructs a new JdbcManyToMany instance.
     *
     * @param connectionManager The ConnectionManager used to manage database connections.
     */
    public JdbcManyToMany(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    /**
     * Retrieves a set of entities related to a pivot entity by a many-to-many relationship.
     *
     * @param entityId         The UUID of the pivot entity.
     * @param sql              The SQL query to execute.
     * @param rowMapper        The RowMapper used to map ResultSet rows to entities.
     * @param exceptionMessage The exception message to be thrown if an error occurs.
     * @return A set of entities related to the pivot entity.
     * @throws EntityNotFoundException If the entities are not found.
     */
    protected Set<T> getByPivot(
        UUID entityId, String sql, RowMapper<T> rowMapper, String exceptionMessage) {
        try (Connection connection = connectionManager.get();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, entityId, Types.OTHER);
            ResultSet resultSet = statement.executeQuery();

            Set<T> entities = new LinkedHashSet<>();
            while (resultSet.next()) {
                entities.add(rowMapper.mapRow(resultSet));
            }
            return entities;
        } catch (SQLException throwables) {
            throw new EntityNotFoundException(exceptionMessage);
        }
    }

    /**
     * Executes an update operation for a many-to-many relationship.
     *
     * @param firstId          The UUID of the first entity.
     * @param secondId         The UUID of the second entity.
     * @param sql              The SQL query to execute.
     * @param exceptionMessage The exception message to be thrown if an error occurs.
     * @return true if the update was successful, false otherwise.
     * @throws EntityDeleteException If the update operation fails.
     */
    protected boolean executeUpdate(
        UUID firstId, UUID secondId, String sql, String exceptionMessage) {
        try (Connection connection = connectionManager.get();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, firstId, Types.OTHER);
            statement.setObject(2, secondId, Types.OTHER);
            return statement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throw new EntityDeleteException(exceptionMessage);
        }
    }
}
