package com.k1fl1k.persistence.repository.mapper.impl;

import com.k1fl1k.persistence.entity.Sections;
import com.k1fl1k.persistence.repository.mapper.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * RowMapper implementation for mapping ResultSet rows to Sections objects.
 */
@Component
public class SectionRowMapper implements RowMapper<Sections> {

    /**
     * Maps a row of a ResultSet to a Sections object.
     *
     * @param rs The ResultSet containing the row to be mapped.
     * @return A Sections object mapped from the ResultSet row.
     * @throws SQLException if a SQLException occurs during the mapping process.
     */
    @Override
    public Sections mapRow(ResultSet rs) throws SQLException {
        return new Sections(
            UUID.fromString(rs.getString("id")),
            rs.getString("name"),
            UUID.fromString(rs.getString("categoryId"))
        );
    }
}
