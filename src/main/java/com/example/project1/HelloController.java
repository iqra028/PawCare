package com.example.project1;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HelloController {
    @FXML
    private Button signUpButton;
    @FXML
    private Button logInButton;
    @FXML
    private Button signUpButton1;
    @FXML
    private Button logInButton1;

    private static final Logger LOGGER = Logger.getLogger(HelloController.class.getName());

    public HelloController() {}

    @FXML
    private void initialize() {
        signUpButton.setOnAction(event -> openSignUpPage());
        logInButton.setOnAction(event -> openLogInPage());
        signUpButton1.setOnAction(event -> openSignUpPage());
        logInButton1.setOnAction(event -> openLogInPage());
    }

    private void openSignUpPage() {
        try {
            HelloApplication.getInstance().changeScene("Select-UserType.fxml");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to change scene to Select-UserType.fxml", e);
        }
    }

    private void openLogInPage() {
        try {
            HelloApplication.getInstance().changeScene("login-view.fxml");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to change scene to Select-UserType.fxml", e);
        }
    }
}