package com.example.project1;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;

public class LogInController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button signUpButton;
    @FXML
    private Button logInButton;
    @FXML
    private Button Finish;
    @FXML
    private Button UserLogin;
    @FXML
    private Button RescueCenterLogin;
    @FXML
    private Button VetLogin;

    private HelloApplication helloApplication;

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
            e.printStackTrace();
        }
    }
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (!username.isEmpty() && !password.isEmpty()) {
            showAlert("Login Success", "Welcome, " + username + "!");
        } else {
            showAlert("Login Error", "Please enter both username and password.");
        }
    }

    private void openLogInPage() {
        try {
            HelloApplication.getInstance().changeScene("login-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void openSignUpPage() {
        try {
            HelloApplication.getInstance().changeScene("Select-UserType.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText((String)null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
