package com.k1fl1k.presentation.controller;

import com.k1fl1k.persistence.util.ConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeleteElementsController {

    @FXML
    private CheckBox categoryCheckBox;

    @FXML
    private CheckBox sectionCheckBox;

    @FXML
    private CheckBox toyCheckBox;

    @FXML
    private ListView<String> dataListView;

    @FXML
    private Button deleteButton;

    private final ConnectionManager connectionManager;

    @Autowired
    public DeleteElementsController(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @FXML
    private void loadData() {
        dataListView.getItems().clear();

        if (categoryCheckBox.isSelected()) {
            loadCategory();
        } else if (sectionCheckBox.isSelected()) {
            loadSections();
        } else if (toyCheckBox.isSelected()) {
            loadToy();
        } else {
            showAlert(Alert.AlertType.WARNING, "Попередження", "Будь ласка, виберіть тип елементу для завантаження даних.");
        }
    }

    private void loadToy() {
        try (Connection connection = connectionManager.get();
            PreparedStatement statement = connection.prepareStatement("SELECT name FROM toy")) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                dataListView.getItems().add(name);
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Помилка", "Помилка при завантаженні подій: " + e.getMessage());
        }
    }

    private void loadSections() {
        try (Connection connection = connectionManager.get();
            PreparedStatement statement = connection.prepareStatement("SELECT name FROM sections")) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                dataListView.getItems().add(name);
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Помилка", "Помилка при завантаженні персон: " + e.getMessage());
        }
    }

    private void loadCategory() {
        try (Connection connection = connectionManager.get();
            PreparedStatement statement = connection.prepareStatement("SELECT name FROM category")) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                dataListView.getItems().add(name);
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Помилка", "Помилка при завантаженні описів: " + e.getMessage());
        }
    }


    @FXML
    private void deleteSelected() {
        String selectedItem = dataListView.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            showAlert(Alert.AlertType.WARNING, "Попередження", "Будь ласка, виберіть елемент для видалення.");
            return;
        }

        if (categoryCheckBox.isSelected() || sectionCheckBox.isSelected() || toyCheckBox.isSelected()) {
            // Якщо обрано категорію або розділ, просто видалити його без аналізу формату
            try {
                String name = selectedItem;
                if (categoryCheckBox.isSelected()) {
                    deleteCategory(selectedItem);
                } else if (sectionCheckBox.isSelected()) {
                    deleteSection(selectedItem);
                }
                else if (toyCheckBox.isSelected()) {
                    deleteToy(selectedItem);
                }
            } catch (IllegalArgumentException e) {
                showAlert(Alert.AlertType.ERROR, "Помилка", "Неправильний формат UUID.");
            }
        }
        else {
            showAlert(Alert.AlertType.WARNING, "Попередження", "Будь ласка, виберіть тип елементу для видалення.");
        }
    }

    private void deleteCategory(String name) {
        try (Connection connection = connectionManager.get()) {
            connection.setAutoCommit(false);

            // Query to get the ID of the category by its name
            String query = "SELECT id FROM category WHERE name = ?";

            // Prepare statement for executing the query
            try (PreparedStatement getIdStatement = connection.prepareStatement(query)) {
                // Set the parameter in the prepared statement
                getIdStatement.setString(1, name);

                // Execute the query to get the ID
                ResultSet resultSet = getIdStatement.executeQuery();

                // Check if a row was returned
                if (resultSet.next()) {
                    // Extract the ID from the result set
                    UUID id = UUID.fromString(resultSet.getString("id"));

                    try (PreparedStatement deleteToys = connection.prepareStatement("DELETE FROM toy WHERE category_id = ?");
                        PreparedStatement deleteSections = connection.prepareStatement("DELETE FROM sections WHERE category_id = ?");
                        PreparedStatement deleteCategory = connection.prepareStatement("DELETE FROM category WHERE id = ?")) {

                        // Delete related records in toy table
                        deleteToys.setObject(1, id);
                        deleteToys.executeUpdate();

                        // Delete related records in sections table
                        deleteSections.setObject(1, id);
                        deleteSections.executeUpdate();

                        // Delete the category
                        deleteCategory.setObject(1, id);
                        int rowsAffected = deleteCategory.executeUpdate();

                        if (rowsAffected > 0) {
                            connection.commit(); // Commit the transaction
                            showAlert(Alert.AlertType.INFORMATION, "Успіх", "Категорію успішно видалено.");
                            loadData();
                        } else {
                            connection.rollback(); // Rollback the transaction
                            showAlert(Alert.AlertType.ERROR, "Помилка", "Помилка при видаленні категорії.");
                        }
                    } catch (SQLException e) {
                        connection.rollback(); // Rollback the transaction on error
                        showAlert(Alert.AlertType.ERROR, "Помилка", "Помилка при видаленні категорії: " + e.getMessage());
                    }
                } else {
                    showAlert(Alert.AlertType.ERROR, "Помилка", "Категорію не знайдено.");
                }
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Помилка", "Помилка при видаленні категорії: " + e.getMessage());
        }
    }


    private void deleteSection(String name) {
        try (Connection connection = connectionManager.get();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM sections WHERE name = ?")) {
            statement.setString(1, name);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Успіх", "Секцію успішно видалено.");
                loadData();
            } else {
                showAlert(Alert.AlertType.ERROR, "Помилка", "Помилка при видаленні секції.");
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Помилка", "Помилка при видаленні секції: " + e.getMessage());
        }
    }

    private void deleteToy(String name) {
        try (Connection connection = connectionManager.get();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM toy WHERE name = ?")) {
            statement.setString(1, name);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Успіх", "Іграшку успішно видалено.");
                loadData();
            } else {
                showAlert(Alert.AlertType.ERROR, "Помилка", "Помилка при видаленні іграшки.");
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Помилка", "Помилка при видаленні іграшки: " + e.getMessage());
        }
    }


    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
