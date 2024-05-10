package com.k1fl1k.persistence.repository.impl.jdbc;

import com.k1fl1k.persistence.entity.Toy;
import com.k1fl1k.persistence.repository.GenericJdbcRepository;
import com.k1fl1k.persistence.repository.contract.TableNames;
import com.k1fl1k.persistence.repository.contract.ToyRepository;
import com.k1fl1k.persistence.repository.mapper.impl.ToyRowMapper;
import com.k1fl1k.persistence.util.ConnectionManager;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class ToyRepositoryImpl extends GenericJdbcRepository<Toy> implements ToyRepository {

    private final JdbcManyToMany<Toy> jdbcManyToMany;
    private final ToyRowMapper toyRowMapper;

    public ToyRepositoryImpl(
        ConnectionManager connectionManager,
        ToyRowMapper toyRowMapper,
        JdbcManyToMany<Toy> jdbcManyToMany) {
        super(connectionManager, toyRowMapper, TableNames.TOY.getName());
        this.jdbcManyToMany = jdbcManyToMany;
        this.toyRowMapper = toyRowMapper;
    }

    @Override
    protected List<String> tableAttributes() {
        return List.of(
            "name",
            "description",
            "price",
            "category_id",
            "manufacture_id"
        );
    }

    @Override
    protected List<Object> tableValues(Toy toy) {
        return List.of(
            toy.name(),
            toy.description(),
            toy.price(),
            toy.categoryId(),
            toy.manufactureId());
    }

    @Override
    public Set<Toy> findAllToysCategory(UUID categoryId) {
        final String sql = """
        SELECT t.id, t.name, t.description, t.price, t.category_id, t.manufacture_id
        FROM toy t
        WHERE t.category_id = ?;
        """;

        return jdbcManyToMany.getByPivot(
            categoryId,
            sql,
            toyRowMapper,
            "Error while getting all toys for category id: " + categoryId);
    }


    @Override
    public boolean attach(UUID toyId, UUID categoryId) {
        final String sql =
            """
            UPDATE toy
            SET category_id = ?
            WHERE id = ?;
            """;
        return jdbcManyToMany.executeUpdate(
            categoryId, toyId, sql, "Помилка при додаванні категорії до іграшки");
    }

    @Override
    public boolean detach(UUID toyId, UUID categoryId) {
        final String sql =
            """
            UPDATE toy
            SET category_id = NULL
            WHERE id = ? AND category_id = ?;
            """;
        return jdbcManyToMany.executeUpdate(
            toyId, categoryId, sql, "Помилка при видаленні категорії з іграшки");
    }
}
