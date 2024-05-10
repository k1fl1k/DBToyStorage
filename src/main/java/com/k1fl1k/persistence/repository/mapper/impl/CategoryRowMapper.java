package com.k1fl1k.persistence.repository.mapper.impl;

import com.k1fl1k.persistence.entity.Category;
import com.k1fl1k.persistence.repository.mapper.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class CategoryRowMapper implements RowMapper<Category> {

    @Override
    public Category mapRow(ResultSet rs) throws SQLException {
        return new Category(
            UUID.fromString(rs.getString("id")),
            rs.getString("name"),
            rs.getString("description")
        );
    }
}
