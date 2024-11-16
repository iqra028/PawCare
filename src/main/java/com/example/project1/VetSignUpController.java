package com.example.project1;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VetSignUpController {
    @FXML
    private TextField usernameField;
    @FXML
    private TextField namefield;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField LocationField;
    @FXML
    private TextField PhoneField;
    @FXML
    private Button signUpButton;
    @FXML
    private Button logInButton;
    @FXML
    private Button Finish;

    private static final Logger LOGGER = Logger.getLogger(VetSignUpController.class.getName());

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

    private void handleSignUp() {
        String username = usernameField.getText();
        String name = namefield.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String Location = LocationField.getText();
        String PhoneNumber = PhoneField.getText();

        if (!username.isEmpty() && !email.isEmpty() && !password.isEmpty() && !Location.isEmpty() && !PhoneNumber.isEmpty()) {
            showAlert("Success", "Sign-up successful!");
        } else {
            showAlert("Error", "All fields are required.");
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

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}