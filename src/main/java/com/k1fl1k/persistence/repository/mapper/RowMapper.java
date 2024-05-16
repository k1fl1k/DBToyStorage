package com.k1fl1k.persistence.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Interface for mapping rows of a ResultSet to objects of type T.
 *
 * @param <T> The type of object to map the ResultSet rows to.
 */
public interface RowMapper<T> {

    /**
     * Maps a row of a ResultSet to an object of type T.
     *
     * @param rs The ResultSet containing the row to be mapped.
     * @return An object of type T mapped from the ResultSet row.
     * @throws SQLException if a SQLException occurs during the mapping process.
     */
    T mapRow(ResultSet rs) throws SQLException;
}
