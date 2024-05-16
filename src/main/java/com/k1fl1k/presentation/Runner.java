package com.k1fl1k.presentation;

import atlantafx.base.theme.NordDark;
import atlantafx.base.theme.NordLight;
import com.k1fl1k.persistence.ApplicationConfig;
import com.k1fl1k.persistence.util.ConnectionManager;
import com.k1fl1k.persistence.util.DatabaseInitializer;
import com.k1fl1k.presentation.util.SpringFXMLLoader;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * The {@code Runner} class represents the entry point for the JavaFX application.
 * It initializes the Spring context and launches the JavaFX application.
 */
public class Runner extends Application {

    /** The Spring application context. */
    public static AnnotationConfigApplicationContext springContext;

    /**
     * The main entry point for the JavaFX application.
     *
     * @param stage The primary stage for the JavaFX application.
     * @throws Exception If an error occurs during application startup.
     */
    @Override
    public void start(Stage stage) throws Exception {
        Application.setUserAgentStylesheet(new NordLight().getUserAgentStylesheet());
        Application.setUserAgentStylesheet(new NordDark().getUserAgentStylesheet());
        var fxmlLoader = new SpringFXMLLoader(springContext);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
        var mainFxmlResource = Runner.class.getResource("view/signIn.fxml");
        Parent parent = (Parent) fxmlLoader.load(mainFxmlResource);
        Scene scene = new Scene(parent, 400, 370);
        scene.setFill(Color.web("#8d642c"));
        stage.setTitle("Склад іграшок");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main entry point of the Java application.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        springContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        var connectionManager = springContext.getBean(ConnectionManager.class);
        var databaseInitializer = springContext.getBean(DatabaseInitializer.class);

        try {
            databaseInitializer.init();
            launch(args);
        } finally {
            connectionManager.closePool();
        }
    }
}
