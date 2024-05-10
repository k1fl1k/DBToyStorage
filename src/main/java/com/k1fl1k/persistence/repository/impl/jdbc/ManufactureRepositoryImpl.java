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

@Repository
public class ManufactureRepositoryImpl extends GenericJdbcRepository<Manufacture> implements
    ManufactureRepository {
    private final ConnectionManager connectionManager;
    private final ManufactureRowMapper manufactureRowMapper;
    private final JdbcManyToMany<Manufacture> jdbcManyToMany;

    public ManufactureRepositoryImpl(
        ConnectionManager connectionManager,
        ManufactureRowMapper manufactureRowMapper,
        JdbcManyToMany<Manufacture> jdbcManyToMany) {
        super(connectionManager, manufactureRowMapper, TableNames.MANUFACTURE.getName());
        this.manufactureRowMapper = manufactureRowMapper;
        this.jdbcManyToMany = jdbcManyToMany;
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
    protected List<Object> tableValues(Manufacture manufacture) {
        return List.of(
            manufacture.name(),
            manufacture.description()
        );
    }

    @Override
    public Optional<Manufacture> findByName(String name) {
        return findBy("name", name);
    }

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
