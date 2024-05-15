package com.k1fl1k.presentation.controller;

import com.k1fl1k.persistence.util.ConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Component;

@Component
public class CartController {

    @FXML
    private VBox cartVBox;

    @FXML
    private Label productInfoLabel;

    private Connection connection;

    private final ConnectionManager connectionManager;

    private UUID userId;

    public CartController(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    @FXML
    public void initialize() {
        setConnection(connection);
        displayProductInfo(userId);
    }

    public void setConnection(Connection connection) {
        this.connection = connectionManager.get();
    }

    @FXML
    private void displayProductInfo(UUID userId) {
        String sql = """
        SELECT t.name, t.description, t.price 
        FROM toy t 
        JOIN cart c ON t.id = c.toy_id
        WHERE c.client_id = ?
        """;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setObject(1, userId);
            ResultSet resultSet = statement.executeQuery();

            StringBuilder productInfoBuilder = new StringBuilder();
            while (resultSet.next()) {
                String productName = resultSet.getString("name");
                String productDescription = resultSet.getString("description");
                double productPrice = resultSet.getDouble("price");

                // Додавання інформації про товар до загального рядка
                productInfoBuilder.append("Назва: ").append(productName).append("\nОпис: ").append(productDescription)
                    .append("\nЦіна: ").append(productPrice).append("\n\n");
            }

            // Виведення інформації про товари у Label
            if (productInfoBuilder.length() > 0) {
                productInfoLabel.setText(productInfoBuilder.toString());
            } else {
                productInfoLabel.setText("Не вдалось загрузити корзину, або вона пуста. Повторіть спробу!");
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clearCart() {
        // Видаляємо всі записи з таблиці cart
        String deleteSql = "DELETE FROM cart WHERE client_id = ?";

        try {
            PreparedStatement deleteStatement = connection.prepareStatement(deleteSql);
            deleteStatement.setObject(1, userId);
            int deletedRows = deleteStatement.executeUpdate();

            if (deletedRows > 0) {
                // Якщо видалення пройшло успішно, оновлюємо інформацію в Label
                productInfoLabel.setText("Корзина очищена.");
            } else {
                productInfoLabel.setText("Корзина вже порожня.");
            }

            deleteStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        cartVBox.getChildren().clear();
    }

    @FXML
    private void checkout() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText("Замовлення було сторено");
        alert.setTitle("Замовлення");
        alert.showAndWait();
    }
}
