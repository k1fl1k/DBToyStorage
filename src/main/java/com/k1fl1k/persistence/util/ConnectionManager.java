package com.k1fl1k.persistence.util;

import jakarta.annotation.PostConstruct;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import org.springframework.stereotype.Component;

/**
 * Manages database connections and provides connection pooling functionality.
 */
@Component
public final class ConnectionManager {

    private static final String URL_KEY = "db.url";
    private static final String USERNAME_KEY = "db.username";
    private static final String PASSWORD_KEY = "db.password";
    private static final String POOL_SIZE_KEY = "db.pool.size";
    private static final Integer DEFAULT_POOL_SIZE = 10;

    private final PropertyManager propertyManager;
    private BlockingDeque<Connection> pool;
    private List<Connection> sourceConnections;

    /**
     * Constructs a ConnectionManager with the specified PropertyManager.
     * @param propertyManager The PropertyManager to use for retrieving database connection properties
     */
    public ConnectionManager(PropertyManager propertyManager) {
        this.propertyManager = propertyManager;
        initConnectionPool();
    }

    /**
     * Initializes the ConnectionManager after construction.
     */
    @PostConstruct
    public void init(){
        loadDriver();
        initConnectionPool();
    }

    /**
     * Retrieves a connection from the connection pool.
     * @return A database Connection
     */
    public Connection get() {
        try {
            return pool.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Closes the connection pool by closing all source connections.
     */
    public void closePool() {
        try {
            for (Connection sourceConnection : sourceConnections) {
                sourceConnection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Loads the JDBC driver
    private void loadDriver() {
        try {
            String driverName = "org.postgresql.Driver";
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Opens a new database connection
    private Connection open() {
        try {
            return DriverManager.getConnection(
                propertyManager.get(URL_KEY),
                propertyManager.get(USERNAME_KEY),
                propertyManager.get(PASSWORD_KEY)
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Initializes the connection pool with a specified size
    private void initConnectionPool() {
        String poolSize = propertyManager.get(POOL_SIZE_KEY);
        int size = poolSize == null ? DEFAULT_POOL_SIZE : Integer.parseInt(poolSize);
        pool = new LinkedBlockingDeque<>(size);
        sourceConnections = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            Connection connection = open();
            Connection proxyConnection = (Connection) Proxy.newProxyInstance(
                ConnectionManager.class.getClassLoader(),
                new Class[]{Connection.class},
                (proxy, method, args) -> method.getName().equals("close")
                    ? pool.add((Connection) proxy)
                    : method.invoke(connection, args));
            pool.add(proxyConnection);
            sourceConnections.add(connection);
        }
    }
}
