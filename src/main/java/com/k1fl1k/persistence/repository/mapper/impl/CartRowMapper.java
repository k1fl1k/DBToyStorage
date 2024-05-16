package com.k1fl1k.persistence.repository.mapper.impl;

import com.k1fl1k.persistence.entity.Cart;
import com.k1fl1k.persistence.repository.mapper.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * RowMapper implementation for mapping ResultSet rows to Cart objects.
 */
@Component
public class CartRowMapper implements RowMapper<Cart> {

    /**
     * Maps a row of a ResultSet to a Cart object.
     *
     * @param rs The ResultSet containing the row to be mapped.
     * @return A Cart object mapped from the ResultSet row.
     * @throws SQLException if a SQLException occurs during the mapping process.
     */
    @Override
    public Cart mapRow(ResultSet rs) throws SQLException {
        return new Cart(
            UUID.fromString(rs.getString("id")),
            UUID.fromString(rs.getString("clientId")),
            UUID.fromString(rs.getString("toyId"))
        );
    }
}
