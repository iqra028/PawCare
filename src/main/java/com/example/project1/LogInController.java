package com.example.project1;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.IOException;

public class LogInController {
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button signUpButton;
    @FXML
    private Button logInButton;
    @FXML
    private Button Finish;
//    @FXML
//    private Button UserLogin;
//    @FXML
//    private Button RescueCenterLogin;
//    @FXML
//    private Button VetLogin;

    private static final Logger LOGGER = Logger.getLogger(LogInController.class.getName());

    @FXML
    private void initialize() {
        signUpButton.setOnAction(event -> openSignUpPage());
        logInButton.setOnAction(event -> openLogInPage());
        Finish.setOnAction(event -> openUserHomePage());
    }
    private void openUserHomePage() {
        try {
            HelloApplication.getInstance().changeScene("UserHomeScreen.fxml");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to change scene to Select-UserType.fxml", e);
        }
    }

    private void handleLogin() {
        String username = emailField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() && password.isEmpty()) {
            LOGGER.log(Level.SEVERE, "Failed to change scene to Select-UserType.fxml", (Throwable) null);
        }
    }

    private void openLogInPage() {
        try {
            HelloApplication.getInstance().changeScene("login-view.fxml");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to change scene to Select-UserType.fxml", e);
        }
    }
    private void openSignUpPage() {
        try {
            HelloApplication.getInstance().changeScene("Select-UserType.fxml");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to change scene to Select-UserType.fxml", e);
        }
    }

}
