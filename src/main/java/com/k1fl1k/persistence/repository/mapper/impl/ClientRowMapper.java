package com.k1fl1k.persistence.repository.mapper.impl;

import com.k1fl1k.persistence.entity.Client;
import com.k1fl1k.persistence.repository.mapper.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class ClientRowMapper implements RowMapper<Client> {

    @Override
    public Client mapRow(ResultSet rs) throws SQLException {
        return new Client(
            UUID.fromString(rs.getString("id")),
            rs.getString("name"),
            rs.getString("phone"),
            rs.getString("address"),
            UUID.fromString(rs.getString("section_id"))
        );
    }
}
