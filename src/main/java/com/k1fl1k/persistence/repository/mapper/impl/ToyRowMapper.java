package com.k1fl1k.persistence.repository.mapper.impl;

import com.k1fl1k.persistence.entity.Toy;
import com.k1fl1k.persistence.repository.mapper.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * RowMapper implementation for mapping ResultSet rows to Toy objects.
 */
@Component
public class ToyRowMapper implements RowMapper<Toy> {

    /**
     * Maps a row of a ResultSet to a Toy object.
     *
     * @param rs The ResultSet containing the row to be mapped.
     * @return A Toy object mapped from the ResultSet row.
     * @throws SQLException if a SQLException occurs during the mapping process.
     */
    @Override
    public Toy mapRow(ResultSet rs) throws SQLException {
        return new Toy(
            UUID.fromString(rs.getString("id")),
            rs.getString("name"),
            rs.getString("description"),
            rs.getFloat("price"),
            UUID.fromString(rs.getString("category_id")),
            UUID.fromString(rs.getString("manufacture_id"))
        );
    }
}
