//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.project1;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class SignUpController {
    @FXML
    private TextField usernameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;

    public SignUpController() {
    }

    @FXML
    private void initialize() {
    }

    private void handleSignUp() {
        String username = this.usernameField.getText();
        String email = this.emailField.getText();
        String password = this.passwordField.getText();
        if (!username.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
            this.showAlert("Success", "Sign-up successful!");
        } else {
            this.showAlert("Error", "All fields are required.");
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
