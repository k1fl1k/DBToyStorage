package com.k1fl1k.persistence.repository.mapper.impl;

import com.k1fl1k.persistence.entity.Toy;
import com.k1fl1k.persistence.repository.mapper.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class ToyRowMapper implements RowMapper<Toy> {

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
