package com.k1fl1k.presentation.util;

import javafx.fxml.FXMLLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import java.io.IOException;
import java.net.URL;

/**
 * The {@code SpringFXMLLoader} class is a utility class for loading FXML files with Spring-managed controllers.
 * It uses an ApplicationContext to retrieve controller instances from the Spring context.
 */
public class SpringFXMLLoader {
    private final ApplicationContext context;
    private static final Logger logger = LoggerFactory.getLogger(SpringFXMLLoader.class);

    /**
     * Constructs a new {@code SpringFXMLLoader} with the specified ApplicationContext.
     *
     * @param context The ApplicationContext to use for loading FXML files.
     */
    public SpringFXMLLoader(ApplicationContext context) {
        this.context = context;
    }

    /**
     * Loads an FXML file from the specified URL and returns the loaded object.
     *
     * @param url The URL of the FXML file to load.
     * @return The loaded object.
     * @throws IOException If an error occurs during loading.
     */
    public Object load(URL url) throws IOException {
        FXMLLoader loader = new FXMLLoader(url);
        loader.setControllerFactory(context::getBean);

        try {
            return loader.load();
        } catch (IOException e) {
            logger.error("Error loading FXML from URL: {}", url, e);
            throw e;
        }
    }
}
