package com.k1fl1k.persistence.repository.mapper.impl;

import com.k1fl1k.persistence.entity.Manufacture;
import com.k1fl1k.persistence.repository.mapper.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class ManufactureRowMapper implements RowMapper<Manufacture> {

    @Override
    public Manufacture mapRow(ResultSet rs) throws SQLException {
        return new Manufacture(
            UUID.fromString(rs.getString("id")),
            rs.getString("name"),
            rs.getString("description")
        );
    }
}
