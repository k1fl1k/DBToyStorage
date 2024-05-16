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
 * Utility class for executing one-to-many database operations using JDBC.
 *
 * @param <T> The type of entity handled by this class.
 */
@Component
public class JdbcOneToMany<T> {
    private final ConnectionManager connectionManager;

    /**
     * Constructs a new JdbcOneToMany instance.
     *
     * @param connectionManager The ConnectionManager used to manage database connections.
     */
    public JdbcOneToMany(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    /**
     * Retrieves a set of entities related to a parent entity by a foreign key.
     *
     * @param parentId         The UUID of the parent entity.
     * @param sql              The SQL query to execute.
     * @param rowMapper        The RowMapper used to map ResultSet rows to entities.
     * @param exceptionMessage The exception message to be thrown if an error occurs.
     * @return A set of entities related to the parent entity.
     * @throws EntityNotFoundException If the entities are not found.
     */
    protected Set<T> getByForeignKey(
        UUID parentId, String sql, RowMapper<T> rowMapper, String exceptionMessage) {
        try (Connection connection = connectionManager.get();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, parentId, Types.OTHER);
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
     * Executes a batch update operation.
     *
     * @param parentId         The UUID of the parent entity.
     * @param childIds         The set of UUIDs of the child entities.
     * @param sql              The SQL query to execute.
     * @param exceptionMessage The exception message to be thrown if an error occurs.
     * @return true if the update was successful, false otherwise.
     * @throws EntityDeleteException If the update operation fails.
     */
    protected boolean executeUpdate(
        UUID parentId, Set<UUID> childIds, String sql, String exceptionMessage) {
        try (Connection connection = connectionManager.get();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            for (UUID childId : childIds) {
                statement.setObject(1, parentId, Types.OTHER);
                statement.setObject(2, childId, Types.OTHER);
                statement.addBatch();
            }
            int[] updateCounts = statement.executeBatch();
            int totalUpdates = 0;
            for (int count : updateCounts) {
                totalUpdates += count;
            }
            return totalUpdates > 0;
        } catch (SQLException throwables) {
            throw new EntityDeleteException(exceptionMessage);
        }
    }
}
