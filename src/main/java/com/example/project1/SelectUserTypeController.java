package com.example.project1;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SelectUserTypeController {

    @FXML
    private Button signUpButton;
    @FXML
    private Button logInButton;
    @FXML
    private Button userButton;
    @FXML
    private Button vetButton;
    @FXML
    private Button rescueCenterButton;

    private static final Logger LOGGER = Logger.getLogger(SelectUserTypeController.class.getName());

    @FXML
    public void initialize() {
        signUpButton.setOnAction(event -> openSignUpPage());
        logInButton.setOnAction(event -> openLogInPage());
        userButton.setOnAction(event -> handleUserSelection());
        vetButton.setOnAction(event -> handleVetSelection());
        rescueCenterButton.setOnAction(event -> handleRescueCenterSelection());
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

    private void handleUserSelection() {
        try {
            HelloApplication.getInstance().changeScene("User-signup.fxml");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to change scene to Select-UserType.fxml", e);

        }
    }

    private void handleVetSelection() {
        try {
            HelloApplication.getInstance().changeScene("Vet-signup.fxml");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to change scene to Select-UserType.fxml", e);

        }
    }

    private void handleRescueCenterSelection() {
        try {
            HelloApplication.getInstance().changeScene("RescueCentre-signup.fxml");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to change scene to Select-UserType.fxml", e);
        }
    }
}
