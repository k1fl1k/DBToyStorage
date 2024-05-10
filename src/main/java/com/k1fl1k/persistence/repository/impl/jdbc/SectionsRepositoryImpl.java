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

@Repository
public class SectionsRepositoryImpl extends GenericJdbcRepository<Sections> implements
    SectionsRepository {
    private final ConnectionManager connectionManager;

    public SectionsRepositoryImpl(
        ConnectionManager connectionManager,
        SectionRowMapper sectionRowMapper) {
        super(connectionManager, sectionRowMapper, TableNames.SECTIONS.getName());
        this.connectionManager = connectionManager;
    }

    @Override
    protected List<String> tableAttributes() {
        return List.of(
            "name",
            "description"
        );
    }

    @Override
    protected List<Object> tableValues(Sections sections) {
        return List.of(
            sections.name(),
            sections.categoryId()
        );
    }

    @Override
    public Optional<Sections> findByName(String name) {
        return findBy("name", name);
    }

    @Override
    public Optional<Sections> findByDescription(String description) {
        return super.findBy("description", description);
    }

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

