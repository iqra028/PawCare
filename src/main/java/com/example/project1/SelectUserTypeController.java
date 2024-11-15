package com.example.project1;

import javafx.fxml.FXML;
import javafx.scene.control.Button;


import java.io.IOException;

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

    private void handleUserSelection() {
        try {
            HelloApplication.getInstance().changeScene("User-signup.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleVetSelection() {
        try {
            HelloApplication.getInstance().changeScene("Vet-signup.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleRescueCenterSelection() {
        try {
            HelloApplication.getInstance().changeScene("RescueCentre-signup.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
