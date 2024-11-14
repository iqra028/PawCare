package com.example.project1;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;

public class SignUpController {

    private static SignUpController instance;

   // private SignUpController() {}

    public static SignUpController getInstance() {
        if (instance == null) {
            instance = new SignUpController();
        }
        return instance;
    }

    @FXML
    private TextField usernameField;
    @FXML
    private TextField usernameField1; // Name field
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private CheckBox termsCheckBox;
    @FXML
    private Button signUpButton;
    @FXML
    private Button logInButton;

    private HelloApplication helloApplication;

    @FXML
    private void initialize() {
        signUpButton.setOnAction(event -> handleSignUp());
        logInButton.setOnAction(event -> openLogInPage());
    }

    private void handleSignUp() {
        String username = usernameField.getText();
        String name = usernameField1.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (username.isEmpty() || name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert("Error", "All fields are required.");
        } else if (!password.equals(confirmPassword)) {
            showAlert("Error", "Passwords do not match.");
        } else if (!termsCheckBox.isSelected()) {
            showAlert("Error", "You must agree to the Terms of Agreement.");
        } else {
            showAlert("Success", "Sign-up successful!");
            // Add logic here to save the User object to your data storage (e.g., database or file)
        }
    }

    private void openLogInPage() {
        try {
            helloApplication.changeScene("/resources/com.example.project1/images/login-view.fxml");
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
