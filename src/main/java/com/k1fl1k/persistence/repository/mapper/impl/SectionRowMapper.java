package com.k1fl1k.persistence.repository.mapper.impl;

import com.k1fl1k.persistence.entity.Sections;
import com.k1fl1k.persistence.repository.mapper.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class SectionRowMapper implements RowMapper<Sections> {

    @Override
    public Sections mapRow(ResultSet rs) throws SQLException {
        return new Sections(
            UUID.fromString(rs.getString("id")),
            rs.getString("name"),
            UUID.fromString(rs.getString("categoryId"))
        );
    }
}
