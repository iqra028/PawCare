package com.example.project1;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;

public class SignUpController {
    @FXML
    private TextField usernameField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField LocationField;
    @FXML
    private PasswordField NumField;

    @FXML
    private Button signUpButton;
    @FXML
    private Button logInButton;
    @FXML
    private Button Finish;

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

    private void handleSignUp() {
        String username = usernameField.getText();
        String name = nameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String Location = LocationField.getText();
        String PhoneNumber = NumField.getText();

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
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}