package com.k1fl1k.persistence.repository.mapper.impl;

import com.k1fl1k.persistence.entity.Cart;
import com.k1fl1k.persistence.repository.mapper.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class CartRowMapper implements RowMapper<Cart> {

    @Override
    public Cart mapRow(ResultSet rs) throws SQLException {
        return new Cart(
            UUID.fromString(rs.getString("id")),
            UUID.fromString(rs.getString("clientId")),
            UUID.fromString(rs.getString("toyId"))
        );
    }
}
