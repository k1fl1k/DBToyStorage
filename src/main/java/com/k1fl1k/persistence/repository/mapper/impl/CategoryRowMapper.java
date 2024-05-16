package com.k1fl1k.persistence.repository.mapper.impl;

import com.k1fl1k.persistence.entity.Category;
import com.k1fl1k.persistence.repository.mapper.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * RowMapper implementation for mapping ResultSet rows to Category objects.
 */
@Component
public class CategoryRowMapper implements RowMapper<Category> {

    /**
     * Maps a row of a ResultSet to a Category object.
     *
     * @param rs The ResultSet containing the row to be mapped.
     * @return A Category object mapped from the ResultSet row.
     * @throws SQLException if a SQLException occurs during the mapping process.
     */
    @Override
    public Category mapRow(ResultSet rs) throws SQLException {
        return new Category(
            UUID.fromString(rs.getString("id")),
            rs.getString("name"),
            rs.getString("description")
        );
    }
}
