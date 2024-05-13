package com.k1fl1k.presentation.controller;

import static com.k1fl1k.presentation.Runner.springContext;

import com.k1fl1k.domain.exception.AuthenticationException;
import com.k1fl1k.domain.exception.UserAlreadyAuthenticatedException;
import com.k1fl1k.domain.service.impl.AuthenticationService;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

@Component
public class SignInController {

    private final AuthenticationService authenticationService;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button signInButton;

    @FXML
    private Button registrationButton;

    @FXML
    private Button exitButton;


    public SignInController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @FXML
    public void initialize() {

    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void signIn() {
        String login = usernameField.getText();
        String password = passwordField.getText();

        try {
            boolean authenticated = authenticationService.authenticate(login, password);

            if (authenticated) {
                showAlert(Alert.AlertType.INFORMATION, "Успіх", "Вхід виконано успішно!");
            } else {
                showAlert(Alert.AlertType.ERROR, "Помилка", "Невірний логін або пароль.");
            }
        } catch (UserAlreadyAuthenticatedException e) {
            showAlert(Alert.AlertType.ERROR, "Помилка", e.getMessage());
        } catch (AuthenticationException e) {
            showAlert(Alert.AlertType.ERROR, "Помилка", "Невірний логін або пароль.");
        }
    }

    @FXML
    private void goToRegistration(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/com/k1fl1k/presentation/view/signUp.fxml"));
            loader.setControllerFactory(springContext::getBean);
            Parent root = loader.load();

            SignUpController signUpController = loader.getController();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image(getClass()
                .getResourceAsStream("/com/k1fl1k/presentation/icon.png")));
            stage.setTitle("Реєстрація");

            ((Node) event.getSource()).getScene().getWindow().hide();

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void exit() {

    }
}
