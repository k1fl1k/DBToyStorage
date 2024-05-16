package com.k1fl1k.persistence.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

/**
 * Utility class for initializing the database.
 * This class executes SQL scripts to initialize the database schema and populate data.
 */
@Component
public final class DatabaseInitializer {
    private final ConnectionManager connectionManager;

    /**
     * Constructs a DatabaseInitializer instance with the specified ConnectionManager.
     * @param connectionManager The ConnectionManager to use for obtaining database connections
     */
    public DatabaseInitializer(ConnectionManager connectionManager){
        this.connectionManager = connectionManager;
    }

    /**
     * Initializes the database by executing SQL scripts for schema and data.
     */
    public void init(){
        try (Connection connection = connectionManager.get();
            Statement statementForDDL = connection.createStatement();
            Statement statementForDML = connection.createStatement()){
            // Execute DDL script to create schema
            statementForDDL.execute(getSQL("ddl.sql"));
            // Execute DML script to populate data
            statementForDML.execute(getSQL("dml.sql"));
        } catch (SQLException throwables){
            // If an SQLException occurs during database initialization, wrap it in a RuntimeException and rethrow
            throw new RuntimeException(throwables);
        }
    }

    /**
     * Reads the content of a SQL script from the classpath resource.
     * @param resourceName The name of the SQL script resource
     * @return The content of the SQL script as a String
     */
    private String getSQL(final String resourceName){
        return new BufferedReader(
            new InputStreamReader(
                Objects.requireNonNull(
                    ConnectionManager.class.getClassLoader().getResourceAsStream(resourceName)))
        ).lines().collect(Collectors.joining("\n"));
    }
}
