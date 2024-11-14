package com.example.project1;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;

public class LogInController {

    private static LogInController instance;

    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private CheckBox termsCheckBox;
    @FXML
    private Button signUpButton;
    @FXML
    private Button loginButton;

    private HelloApplication helloApplication;

    private LogInController() { }

    public static LogInController getInstance()
    {
        if (instance == null) {
            instance = new LogInController();
        }
        return instance;
    }

    public void setHelloApplication(HelloApplication helloApplication) {
        this.helloApplication = helloApplication;
    }

    @FXML
    private void initialize() {
        signUpButton.setOnAction(event -> openSignUpPage());
        loginButton.setOnAction(event -> handleLogin());
    }

    private void handleLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (!email.isEmpty() && !password.isEmpty() && termsCheckBox.isSelected()) {
            boolean loginSuccessful = false;

/*            if (authenticateUser(email, password)) {
                showAlert("Login Success", "Welcome, User!");
                loginSuccessful = true;
            } else if (authenticateVet(email, password)) {
                showAlert("Login Success", "Welcome, Vet!");
                loginSuccessful = true;
            } else if (authenticateRescueCenter(email, password)) {
                showAlert("Login Success", "Welcome, Rescue Center!");
                loginSuccessful = true;
            }*/

            if (!loginSuccessful) {
                showAlert("Login Error", "Invalid email or password.");
            }
        } else {
            showAlert("Login Error", "Please enter both email and password and agree to the Terms of Agreement.");
        }
    }
/*
    private boolean authenticateUser(String email, String password) {
        // Example logic to check against stored `User` credentials
        User storedUser = getUserByEmail(email);
        return storedUser != null && storedUser.getPassword().equals(password);
    }

    private boolean authenticateVet(String email, String password) {
        // Example logic to check against stored `Vets` credentials
        Vets storedVet = getVetByEmail(email);
        return storedVet != null && storedVet.getPassword().equals(password);
    }

    private boolean authenticateRescueCenter(String email, String password) {
        // Example logic to check against stored `RescueCenter` credentials
        RescueCenter storedCenter = getRescueCenterByEmail(email);
        return storedCenter != null && storedCenter.getPassword().equals(password);
    }

    private User getUserByEmail(String email) {
        // Query the User data store for a user with this email
        return null;
    }

    private Vets getVetByEmail(String email) {
        // Query the Vets data store for a vet with this email
        return null;
    }

    private RescueCenter getRescueCenterByEmail(String email) {
        // Query the Rescue Center data store for a center with this email
        return null;
    }

    private void openSignUpPage() {
        try {
            HelloApplication.getInstance().changeScene("signup-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
*/
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
