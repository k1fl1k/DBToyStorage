package com.k1fl1k.persistence.repository.impl.jdbc;

import com.k1fl1k.persistence.entity.Category;
import com.k1fl1k.persistence.repository.GenericJdbcRepository;
import com.k1fl1k.persistence.repository.contract.CategoryRepository;
import com.k1fl1k.persistence.repository.contract.TableNames;
import com.k1fl1k.persistence.repository.mapper.impl.CategoryRowMapper;
import com.k1fl1k.persistence.repository.mapper.impl.ToyRowMapper;
import com.k1fl1k.persistence.util.ConnectionManager;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryRepositoryImpl extends GenericJdbcRepository<Category> implements
    CategoryRepository {
    private final ToyRowMapper toyRowMapper;
    private final CategoryRowMapper categoryRowMapper;
    private final JdbcManyToMany<Category> jdbcManyToMany;

    public CategoryRepositoryImpl(
        ConnectionManager connectionManager,
        CategoryRowMapper rowMapper,
        ToyRowMapper toyRowMapper,
        CategoryRowMapper categoryRowMapper,
        JdbcManyToMany<Category> jdbcManyToMany) {
        super(connectionManager, rowMapper, TableNames.CATEGORY.getName());
        this.toyRowMapper = toyRowMapper;
        this.jdbcManyToMany = jdbcManyToMany;
        this.categoryRowMapper = categoryRowMapper;
    }

    @Override
    protected List<String> tableAttributes() {
        return List.of("name", "description");
    }

    @Override
    protected List<Object> tableValues(Category category) {
        return List.of(category.name(), category.description());
    }

    @Override
    public Set<Category> findAllByToyId(UUID toyId) {
        final String sql = """
        SELECT c.id,
               c.name,
               c.description
          FROM category AS c
               JOIN toy_category AS tc
                 ON c.id = tc.category_id
         WHERE tc.toy_id = ?;
        """;

        // Assuming jdbcManyToMany is a JdbcManyToMany<Category>
        return jdbcManyToMany.getByPivot(
            toyId,
            sql,
            categoryRowMapper, // Use CategoryRowMapper here
            "Error while getting all categories for toy id: " + toyId);
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
