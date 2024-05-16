package com.k1fl1k.persistence.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.springframework.stereotype.Component;

/**
 * Utility class for managing properties.
 * This class loads properties from the application.properties file.
 */
@Component
public final class PropertyManager {

    private static final Properties PROPERTIES = new Properties();

    /**
     * Constructs a PropertyManager instance and loads properties from the application.properties file.
     */
    public PropertyManager() {
        loadProperties();
    }

    /**
     * Retrieves the value of a property by its key.
     * @param key The key of the property to retrieve
     * @return The value of the property corresponding to the given key
     */
    public static String get(String key){
        return PROPERTIES.getProperty(key);
    }

    /**
     * Loads properties from the application.properties file into the PROPERTIES instance.
     * This method is called internally during construction.
     */
    private void loadProperties(){
        try (InputStream applicationProperties = PropertyManager.class.getClassLoader()
            .getResourceAsStream("application.properties")) {
            PROPERTIES.load(applicationProperties);
        } catch (IOException e){
            // If an IOException occurs while reading properties, it is wrapped in a RuntimeException and rethrown
            throw new RuntimeException(e);
        }
    }

    /**
     * Constructs a PropertyManager instance with a specified InputStream and loads properties from it.
     * @param applicationProperties The InputStream containing the properties to load
     */
    public PropertyManager(InputStream applicationProperties) {
        try (applicationProperties) {
            PROPERTIES.load(applicationProperties);
        } catch (IOException e) {
            // If an IOException occurs while reading properties, it is wrapped in a RuntimeException and rethrown
            throw new RuntimeException(e);
        }
    }
}
