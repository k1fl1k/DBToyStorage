package com.k1fl1k.persistence.repository.impl.jdbc;

import com.k1fl1k.persistence.entity.Manufacture;
import com.k1fl1k.persistence.repository.GenericJdbcRepository;
import com.k1fl1k.persistence.repository.contract.ManufactureRepository;
import com.k1fl1k.persistence.repository.contract.TableNames;
import com.k1fl1k.persistence.repository.mapper.impl.ManufactureRowMapper;
import com.k1fl1k.persistence.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

/**
 * JDBC implementation of the ManufactureRepository interface.
 */
@Repository
public class ManufactureRepositoryImpl extends GenericJdbcRepository<Manufacture> implements ManufactureRepository {
    private final ConnectionManager connectionManager;
    private final ManufactureRowMapper manufactureRowMapper;
    private final JdbcManyToMany<Manufacture> jdbcManyToMany;

    /**
     * Constructs a new ManufactureRepositoryImpl.
     *
     * @param connectionManager     The ConnectionManager to be used for database connections.
     * @param manufactureRowMapper  The ManufactureRowMapper to be used for mapping ResultSet rows to Manufacture objects.
     * @param jdbcManyToMany        The JdbcManyToMany to be used for many-to-many operations.
     */
    public ManufactureRepositoryImpl(
        ConnectionManager connectionManager,
        ManufactureRowMapper manufactureRowMapper,
        JdbcManyToMany<Manufacture> jdbcManyToMany) {
        super(connectionManager, manufactureRowMapper, TableNames.MANUFACTURE.getName());
        this.manufactureRowMapper = manufactureRowMapper;
        this.jdbcManyToMany = jdbcManyToMany;
        this.connectionManager = connectionManager;
    }

    /**
     * Retrieves the attributes of the manufacture table.
     *
     * @return A List of Strings representing the attributes of the manufacture table.
     */
    @Override
    protected List<String> tableAttributes() {
        return List.of(
            "name",
            "description"
        );
    }

    /**
     * Retrieves the values of the manufacture table for the specified manufacture entity.
     *
     * @param manufacture The Manufacture object for which to retrieve the values.
     * @return A List of Objects representing the values of the manufacture table for the specified manufacture entity.
     */
    @Override
    protected List<Object> tableValues(Manufacture manufacture) {
        return List.of(
            manufacture.name(),
            manufacture.description()
        );
    }

    /**
     * Finds a manufacture by its name.
     *
     * @param name The name of the manufacture to find.
     * @return An Optional containing the Manufacture object if found, or empty otherwise.
     */
    @Override
    public Optional<Manufacture> findByName(String name) {
        return findBy("name", name);
    }

    /**
     * Attaches a child entity to a parent entity.
     *
     * @param parentId The UUID of the parent entity.
     * @param childId  The UUID of the child entity.
     * @return true if the attachment was successful, false otherwise.
     */
    @Override
    public boolean attach(UUID parentId, UUID childId) {
        String sql = "UPDATE Toy SET manufacture_id = ? WHERE id = ?";
        try (Connection connection = connectionManager.get();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, parentId);
            statement.setObject(2, childId);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            // Handle exception
            return false;
        }
    }

    /**
     * Detaches a child entity from a parent entity.
     *
     * @param parentId The UUID of the parent entity.
     * @param childId  The UUID of the child entity.
     * @return true if the detachment was successful, false otherwise.
     */
    @Override
    public boolean detach(UUID parentId, UUID childId) {
        String sql = "UPDATE Toy SET manufacture_id = NULL WHERE id = ?";
        try (Connection connection = connectionManager.get();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, childId);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            // Handle exception
            return false;
        }
    }
}
