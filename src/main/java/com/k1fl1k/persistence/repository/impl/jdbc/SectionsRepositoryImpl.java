package com.k1fl1k.persistence.repository.impl.jdbc;

import com.k1fl1k.persistence.entity.Sections;
import com.k1fl1k.persistence.repository.GenericJdbcRepository;
import com.k1fl1k.persistence.repository.contract.SectionsRepository;
import com.k1fl1k.persistence.repository.contract.TableNames;
import com.k1fl1k.persistence.repository.mapper.impl.SectionRowMapper;
import com.k1fl1k.persistence.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

/**
 * JDBC implementation of the SectionsRepository interface.
 */
@Repository
public class SectionsRepositoryImpl extends GenericJdbcRepository<Sections> implements SectionsRepository {
    private final ConnectionManager connectionManager;

    /**
     * Constructs a new SectionsRepositoryImpl.
     *
     * @param connectionManager The ConnectionManager to be used for database connections.
     * @param sectionRowMapper  The SectionRowMapper to be used for mapping ResultSet rows to Section objects.
     */
    public SectionsRepositoryImpl(
        ConnectionManager connectionManager,
        SectionRowMapper sectionRowMapper) {
        super(connectionManager, sectionRowMapper, TableNames.SECTIONS.getName());
        this.connectionManager = connectionManager;
    }

    /**
     * Retrieves the attributes of the sections table.
     *
     * @return A List of Strings representing the attributes of the sections table.
     */
    @Override
    protected List<String> tableAttributes() {
        return List.of(
            "name",
            "description"
        );
    }

    /**
     * Retrieves the values of the sections table for the specified sections entity.
     *
     * @param sections The Sections object for which to retrieve the values.
     * @return A List of Objects representing the values of the sections table for the specified sections entity.
     */
    @Override
    protected List<Object> tableValues(Sections sections) {
        return List.of(
            sections.name(),
            sections.categoryId()
        );
    }

    /**
     * Finds a section by its name.
     *
     * @param name The name of the section to find.
     * @return An Optional containing the Section object if found, or empty otherwise.
     */
    @Override
    public Optional<Sections> findByName(String name) {
        return findBy("name", name);
    }

    /**
     * Finds a section by its description.
     *
     * @param description The description of the section to find.
     * @return An Optional containing the Section object if found, or empty otherwise.
     */
    @Override
    public Optional<Sections> findByDescription(String description) {
        return super.findBy("description", description);
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
        String sql = "UPDATE Toy SET section_id = ? WHERE id = ?";
        try (Connection connection = connectionManager.get();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, parentId);
            statement.setObject(2, childId);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            new SQLException(e);
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
        String sql = "UPDATE Toy SET section_id = NULL WHERE id = ?";
        try (Connection connection = connectionManager.get();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, childId);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            new SQLException(e);
            return false;
        }
    }
}
