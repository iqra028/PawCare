package com.example.project1;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.IOException;

public class RescueCenterSignUpController {
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
        private TextField NumField;
        @FXML
        private Button signUpButton;
        @FXML
        private Button logInButton;
        @FXML
        private Button Finish;

    private static final Logger LOGGER = Logger.getLogger(RescueCenterSignUpController.class.getName());

    @FXML
        private void initialize() {
            signUpButton.setOnAction(event -> openSignUpPage());
            logInButton.setOnAction(event -> openLogInPage());
            Finish.setOnAction(event -> openUserHomePage());

        }
        private void openUserHomePage() {
            try {
                HelloApplication.getInstance().changeScene("RescueCenterHomeScreen.fxml");
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Failed to change scene to Select-UserType.fxml", e);

            }
        }

        private void handleSignUp() {
            String username2 = usernameField.getText();
            String name2 = nameField.getText();
            String email2 = emailField.getText();
            String password2 = passwordField.getText();
            String Location2 = LocationField.getText();
            String PhoneNumber2 = NumField.getText();

            if (!username2.isEmpty() && !email2.isEmpty() && !password2.isEmpty() && !Location2.isEmpty() && !PhoneNumber2.isEmpty() && !name2.isEmpty()) {
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
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        }

}
