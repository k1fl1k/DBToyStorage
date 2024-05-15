package com.k1fl1k.presentation.controller;

import static com.k1fl1k.presentation.Runner.springContext;

import com.k1fl1k.persistence.entity.Sections;
import com.k1fl1k.persistence.util.ConnectionManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

import java.sql.*;
import java.util.UUID;

import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MainController {

    @FXML
    private Label userLabel;

    @FXML
    private Label accessLevelLabel;

    @FXML
    private VBox sectionButtons;

    @FXML
    private VBox categoriesButtons;

    @FXML
    private VBox toysButtons;

    @FXML
    private Button addCategoryButton;

    @FXML
    private Button addToyButton;

    @FXML
    private Button addSectionButton;

    private String login;
    private String userId;
    protected UUID idUser;
    private String role;

    private Connection connection;

    private final ConnectionManager connectionManager;
    @Autowired
    public MainController(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @FXML
    public void initialize() {
        setConnection(connection);
        retrieveUserData();
        retrieveSections();
    }

    public void setCurrentUser(String login) {
        this.login = login;
        retrieveUserData();
    }

    @FXML
    private void showAddButtons(String role) {
        if (role != null && (role.equals("moder") || role.equals("admin"))) {
            addCategoryButton.setVisible(true);
            addToyButton.setVisible(true);
            addSectionButton.setVisible(true);
        } else {
            addCategoryButton.setVisible(false);
            addToyButton.setVisible(false);
            addSectionButton.setVisible(false);
        }
    }

    // Метод для отримання даних користувача
    private void retrieveUserData() {
        try {
            if (login != null) {
                Statement statement = connection.createStatement();
                ResultSet userResultSet = statement.executeQuery(
                    "SELECT * FROM users WHERE login = '" + login + "'");
                if (userResultSet.next()) {
                    String loginCurrentUser = userResultSet.getString("login");
                    String roleCurrenUser = userResultSet.getString("role");

                    UUID idUserForCart = (UUID) userResultSet.getObject("id");
                    this.idUser = idUserForCart;

                    userLabel.setText("User: " + loginCurrentUser);
                    accessLevelLabel.setText("Access: " + roleCurrenUser);
                    showAddButtons(roleCurrenUser);
                    this.role = roleCurrenUser;
                    userId = userResultSet.getString("id"); // Збереження userId
                } else {
                    // Якщо користувача не знайдено, можна встановити відповідні повідомлення у лейбли
                    userLabel.setText("User not found");
                    accessLevelLabel.setText("");
                }
                statement.close();
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    // Метод для отримання секцій
    private void retrieveSections() {
        try {
            sectionButtons.getChildren().clear();
            Statement statement = connection.createStatement();
            ResultSet sectionResultSet = statement.executeQuery("SELECT * FROM sections");

            List<Sections> sections = new ArrayList<>();

            while (sectionResultSet.next()) {
                String idStr = sectionResultSet.getString("id");
                UUID id = UUID.fromString(idStr);
                String sectionName = sectionResultSet.getString("name");
                String categoryIdStr = sectionResultSet.getString("category_id");
                UUID categoryId = UUID.fromString(categoryIdStr);
                sections.add(new Sections(id, sectionName, categoryId));
            }

            statement.close();
            sectionResultSet.close();

            for (Sections section : sections) {
                Button sectionButton = new Button(section.getName());
                sectionButton.setOnAction(event -> retrieveCategories(section.getCategoryId().toString()));
                sectionButtons.getChildren().add(sectionButton);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving sections", e);
        }
    }

    // Метод для отримання категорій для заданої секції
    private void retrieveCategories(String categoryId) {
        categoriesButtons.getChildren().clear();
        try {
            Statement statement = connection.createStatement();
            ResultSet categoryResultSet = statement.executeQuery(
                "SELECT * FROM category WHERE id = '" + categoryId + "'");
            while (categoryResultSet.next()) {
                String categoryName = categoryResultSet.getString("name");
                String categoryIdFromResultSet = categoryResultSet.getString("id");
                Button categoryButton = new Button(categoryName);
                categoryButton.setOnAction(event -> {
                    retrieveToys(categoryIdFromResultSet);
                });
                categoriesButtons.getChildren().add(categoryButton);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    private void retrieveToys(String categoryId) {
        try {
            Statement statement = connection.createStatement();
            ResultSet toyResultSet = statement.executeQuery(
                "SELECT * FROM toy WHERE category_id = '" + categoryId + "'");
            while (toyResultSet.next()) {
                String toyId = toyResultSet.getString("id");
                String toyName = toyResultSet.getString("name");
                Button toyButton = new Button(toyName);
                toyButton.setOnAction(event -> {
                    addToCart(UUID.randomUUID().toString(), userId, toyId);
                });
                toysButtons.getChildren().add(toyButton);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void addToCart(String cartId, String userId, String toyId) {
        if (!role.equals("admin")) {
            try {
                // Перетворення рядків на UUID
                UUID cartUUID = UUID.fromString(cartId);
                UUID userUUID = UUID.fromString(userId);
                UUID toyUUID = UUID.fromString(toyId);

                PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO cart (id, client_id, toy_id) VALUES (?, ?, ?)");
                // Встановлення значень параметрів
                statement.setObject(1, cartUUID);
                statement.setObject(2, userUUID);
                statement.setObject(3, toyUUID);
                statement.executeUpdate();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Помилка");
            alert.setContentText("Такого клієнта не існує");
            alert.showAndWait();
        }
    }


    public void setConnection(Connection connection) {
        this.connection = connectionManager.get();
        retrieveUserData();
    }

    @FXML
    private void exit() {
        System.exit(0);
    }

    @FXML
    private void openCart() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/com/k1fl1k/presentation/view/cart.fxml"));
            loader.setControllerFactory(springContext::getBean);
            Parent root = loader.load();

            CartController cartController = loader.getController();
            cartController.setUserId(idUser);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image(getClass()
                .getResourceAsStream("/com/k1fl1k/presentation/icon.png")));
            stage.setTitle("Корзина");

            stage.initModality(Modality.APPLICATION_MODAL);

            stage.showAndWait();
            retrieveSections();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addElementsWindow(String elementType) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/com/k1fl1k/presentation/view/addNewElements.fxml"));
            loader.setControllerFactory(springContext::getBean);
            Parent root = loader.load();

            AddNewElementsController addNewElementsController = loader.getController();

            // Передаємо тип елемента контролеру addNewElementsController
            addNewElementsController.setElementType(elementType);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image(getClass()
                .getResourceAsStream("/com/k1fl1k/presentation/icon.png")));
            stage.setTitle("Додавання елементів");

            stage.initModality(Modality.APPLICATION_MODAL);

            stage.showAndWait();
            retrieveSections();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addToy() {
        addElementsWindow("toy");
    }

    @FXML
    private void addSection() {
        addElementsWindow("sections");
    }

    @FXML
    private void addCategory() {
        addElementsWindow("category");
    }
}
