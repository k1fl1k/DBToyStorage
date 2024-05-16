package com.k1fl1k.persistence.repository.mapper.impl;

import com.k1fl1k.persistence.entity.Manufacture;
import com.k1fl1k.persistence.repository.mapper.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * RowMapper implementation for mapping ResultSet rows to Manufacture objects.
 */
@Component
public class ManufactureRowMapper implements RowMapper<Manufacture> {

    /**
     * Maps a row of a ResultSet to a Manufacture object.
     *
     * @param rs The ResultSet containing the row to be mapped.
     * @return A Manufacture object mapped from the ResultSet row.
     * @throws SQLException if a SQLException occurs during the mapping process.
     */
    @Override
    public Manufacture mapRow(ResultSet rs) throws SQLException {
        return new Manufacture(
            UUID.fromString(rs.getString("id")),
            rs.getString("name"),
            rs.getString("description")
        );
    }
}
