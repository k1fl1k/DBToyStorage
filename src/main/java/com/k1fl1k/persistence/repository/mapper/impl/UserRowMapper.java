package com.k1fl1k.persistence.repository.mapper.impl;

import com.k1fl1k.persistence.entity.Users;
import com.k1fl1k.persistence.entity.Users.UsersRole;
import com.k1fl1k.persistence.repository.mapper.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class UserRowMapper implements RowMapper<Users> {

    @Override
    public Users mapRow(ResultSet rs) throws SQLException {
        String roleName = rs.getString("role");
        UsersRole role = null;
        if (roleName != null) {
            for (UsersRole enumRole : UsersRole.values()) {
                if (enumRole.getName().equals(roleName)) {
                    role = enumRole;
                    break;
                }
            }
        }
        if (role == null) {
            throw new IllegalArgumentException("Invalid role: " + roleName);
        }

        return new Users(
            UUID.fromString(rs.getString("id")),
            rs.getString("login"),
            rs.getString("password"),
            role,
            rs.getString("name"));
    }
}
