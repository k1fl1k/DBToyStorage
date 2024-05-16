package com.k1fl1k.presentation.controller;

import com.k1fl1k.persistence.util.ConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 * The {@code AddNewElementsController} class controls the functionality of adding new elements
 * to the application's database, such as toys, sections, and categories.
 */
@Component
public class AddNewElementsController {

    private static final Logger logger = LoggerFactory.getLogger(AddNewElementsController.class);
    private String elementType;

    @FXML
    private TextField textField1;

    @FXML
    private TextField textField2;

    @FXML
    private TextField textField3;

    @FXML
    private ComboBox<String> comboBox1;

    @FXML
    private ComboBox<String> comboBox2;

    private Connection connection;

    private final ConnectionManager connectionManager;
    /**
     * Constructs a new {@code AddNewElementsController} with the specified {@code ConnectionManager}.
     *
     * @param connectionManager The connection manager to use.
     */
    @Autowired
    public AddNewElementsController(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }
    /**
     * Sets the connection for the controller.
     *
     * @param connection The connection to set.
     */
    public void setConnection(Connection connection) {
        this.connection = connectionManager.get();
    }
    /**
     * Sets the type of the element to be added.
     *
     * @param elementType The type of the element.
     */
    public void setElementType(String elementType) {
        this.elementType = elementType;
        updateElementVisibility();
    }

    private void populateComboBoxes() {
        this.connection = connectionManager.get();
        ObservableList<String> categories = FXCollections.observableArrayList();
        ObservableList<String> manufactures = FXCollections.observableArrayList();

        // Запит на отримання всіх категорій
        String categoryQuery = "SELECT name FROM category";
        try (PreparedStatement categoryStatement = connection.prepareStatement(categoryQuery);
            ResultSet categoryResultSet = categoryStatement.executeQuery()) {
            while (categoryResultSet.next()) {
                String categoryName = categoryResultSet.getString("name");
                categories.add(categoryName);
                logger.info("Category: {}", categoryName); // Використання логера
            }
        } catch (SQLException e) {
            logger.error("Exception occurred while fetching categories: {}", e.getMessage());
            e.printStackTrace();
        }

        // Запит на отримання всіх виробників
        String manufactureQuery = "SELECT name FROM manufacture";
        try (PreparedStatement manufactureStatement = connection.prepareStatement(manufactureQuery);
            ResultSet manufactureResultSet = manufactureStatement.executeQuery()) {
            while (manufactureResultSet.next()) {
                String manufactureName = manufactureResultSet.getString("name");
                manufactures.add(manufactureName);
                logger.info("Manufacture: {}", manufactureName); // Використання логера
            }
        } catch (SQLException e) {
            logger.error("Exception occurred while fetching manufactures: {}", e.getMessage());
            e.printStackTrace();
        }

        // Додавання елементів до ComboBox
        comboBox1.setItems(categories);
        comboBox2.setItems(manufactures);
    }

    private void updateElementVisibility() {
        switch (elementType) {
            case "toy":
                setElementsVisibility(true, true,
                    true, true, true);
                populateComboBoxes();
                break;
            case "sections":
                setElementsVisibility(true, false,
                    false, true, false);
                populateComboBoxes();
                break;
            case "category":
                setElementsVisibility(true, true,
                    false, false, false);
                break;
            default:
                setElementsVisibility(false, false,
                    false, false, false);
                break;
        }
    }

    private void setElementsVisibility(boolean textField1Visible, boolean textField2Visible,
        boolean textField3Visible, boolean comboBox1Visible, boolean comboBox2Visible) {
        textField1.setVisible(textField1Visible);
        textField2.setVisible(textField2Visible);
        textField3.setVisible(textField3Visible);
        comboBox1.setVisible(comboBox1Visible);
        comboBox2.setVisible(comboBox2Visible);
    }

    @FXML
    public void onSave() {
        try {
            switch (elementType) {
                case "toy":
                    saveToy();
                    break;
                case "sections":
                    saveSection();
                    break;
                case "category":
                    saveCategory();
                    break;
                default:
                    // Якщо тип елементу не відомий, видаємо повідомлення про помилку
                    showAlert(Alert.AlertType.ERROR, "Помилка", "Невідомий тип елементу");
                    break;
            }
        } catch (SQLException e) {
            // Обробка винятку SQLException
            showAlert(Alert.AlertType.ERROR, "Помилка", "Помилка при збереженні елементу: " + e.getMessage());
        }
    }

    private void saveToy() {
        UUID id = UUID.randomUUID();
        String name = textField1.getText();
        String description = textField2.getText();
        float price = Float.parseFloat(textField3.getText());
        String categoryName = comboBox1.getValue(); // Отримати ім'я вибраної категорії
        String manufactureName = comboBox2.getValue(); // Отримати ім'я вибраного виробника

        // Отримання id за допомогою запитів SQL
        UUID categoryId = getCategoryID(categoryName);
        UUID manufactureId = getManufactureID(manufactureName);

        String sql = "INSERT INTO toy (id, name, description, price, category_id, manufacture_id) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setObject(1, id);
            statement.setString(2, name);
            statement.setString(3, description);
            statement.setFloat(4, price);
            statement.setObject(5, categoryId);
            statement.setObject(6, manufactureId);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private UUID getCategoryID(String categoryName) {
        String sql = "SELECT id FROM category WHERE name = ?";
        UUID resultSet = getUuid(categoryName, sql);
        if (resultSet != null) {
            return resultSet;
        }
        return null; // Якщо не вдалося знайти id
    }

    private UUID getUuid(String categoryName, String sql) {
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, categoryName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return UUID.fromString(resultSet.getString("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private UUID getManufactureID(String manufactureName) {
        String sql = "SELECT id FROM manufacture WHERE name = ?";
        UUID resultSet = getUuid(manufactureName, sql);
        if (resultSet != null) {
            return resultSet;
        }
        return null; // Якщо не вдалося знайти id
    }



    private void saveSection() throws SQLException {
        UUID id = UUID.randomUUID();
        String name = textField1.getText();
        String categoryName = comboBox1.getValue();

        UUID categoryId = getCategoryID(categoryName);

        String sql = "INSERT INTO sections (id, name, category_id) VALUES (?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setObject(1, id);
            statement.setString(2, name);
            statement.setObject(3, categoryId);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveCategory() throws SQLException {
        UUID id = UUID.randomUUID();
        String name = textField1.getText();
        String description =textField2.getText();

        String sql = "INSERT INTO category (id, name, description) VALUES (?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setObject(1, id);
            statement.setString(2, name);
            statement.setString(3, description);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void onCancel() {
        Stage currentStage = (Stage) textField1.getScene().getWindow();
        currentStage.close();
    }
}
