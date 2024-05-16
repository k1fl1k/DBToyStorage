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

/**
 * JDBC implementation of the ToyRepository interface.
 */
@Repository
public class ToyRepositoryImpl extends GenericJdbcRepository<Toy> implements ToyRepository {

    private final JdbcManyToMany<Toy> jdbcManyToMany;
    private final ToyRowMapper toyRowMapper;

    /**
     * Constructs a new ToyRepositoryImpl.
     *
     * @param connectionManager The ConnectionManager to be used for database connections.
     * @param toyRowMapper      The ToyRowMapper to be used for mapping ResultSet rows to Toy objects.
     * @param jdbcManyToMany    The JdbcManyToMany to be used for many-to-many relationships with toys.
     */
    public ToyRepositoryImpl(
        ConnectionManager connectionManager,
        ToyRowMapper toyRowMapper,
        JdbcManyToMany<Toy> jdbcManyToMany) {
        super(connectionManager, toyRowMapper, TableNames.TOY.getName());
        this.jdbcManyToMany = jdbcManyToMany;
        this.toyRowMapper = toyRowMapper;
    }

    /**
     * Retrieves the attributes of the toy table.
     *
     * @return A List of Strings representing the attributes of the toy table.
     */
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

    /**
     * Retrieves the values of the toy table for the specified toy entity.
     *
     * @param toy The Toy object for which to retrieve the values.
     * @return A List of Objects representing the values of the toy table for the specified toy entity.
     */
    @Override
    protected List<Object> tableValues(Toy toy) {
        return List.of(
            toy.name(),
            toy.description(),
            toy.price(),
            toy.categoryId(),
            toy.manufactureId());
    }

    /**
     * Finds all toys belonging to a specific category.
     *
     * @param categoryId The UUID of the category to which the toys belong.
     * @return A Set of Toy objects belonging to the specified category.
     */
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

    /**
     * Attaches a toy to a category.
     *
     * @param toyId     The UUID of the toy to attach.
     * @param categoryId The UUID of the category to which to attach the toy.
     * @return true if the attachment was successful, false otherwise.
     */
    @Override
    public boolean attach(UUID toyId, UUID categoryId) {
        final String sql =
            """
        UPDATE toy
        SET category_id = ?
        WHERE id = ?;
        """;
        return jdbcManyToMany.executeUpdate(
            categoryId, toyId, sql, "Error while attaching category to toy");
    }

    /**
     * Detaches a toy from a category.
     *
     * @param toyId     The UUID of the toy to detach.
     * @param categoryId The UUID of the category from which to detach the toy.
     * @return true if the detachment was successful, false otherwise.
     */
    @Override
    public boolean detach(UUID toyId, UUID categoryId) {
        final String sql =
            """
        UPDATE toy
        SET category_id = NULL
        WHERE id = ? AND category_id = ?;
        """;
        return jdbcManyToMany.executeUpdate(
            toyId, categoryId, sql, "Error while detaching category from toy");
    }
}
