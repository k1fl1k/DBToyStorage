package com.k1fl1k.persistence.repository.mapper.impl;

import com.k1fl1k.persistence.entity.Client;
import com.k1fl1k.persistence.repository.mapper.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * RowMapper implementation for mapping ResultSet rows to Client objects.
 */
@Component
public class ClientRowMapper implements RowMapper<Client> {

    /**
     * Maps a row of a ResultSet to a Client object.
     *
     * @param rs The ResultSet containing the row to be mapped.
     * @return A Client object mapped from the ResultSet row.
     * @throws SQLException if a SQLException occurs during the mapping process.
     */
    @Override
    public Client mapRow(ResultSet rs) throws SQLException {
        return new Client(
            UUID.fromString(rs.getString("id")),
            rs.getString("name"),
            rs.getString("phone"),
            rs.getString("address")
        );
    }
}
