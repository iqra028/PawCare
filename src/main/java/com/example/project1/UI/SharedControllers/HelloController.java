package com.example.project1.UI.SharedControllers;

import java.io.IOException;

import com.example.project1.BLL.LoginClassCredentials;
import com.example.project1.BLL.PawCare;
import com.example.project1.BLL.RequiresSharedData;
import com.example.project1.HelloApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HelloController implements RequiresSharedData{
    @FXML
    private Button signUpButton;
    @FXML
    private Button logInButton;
    @FXML
    private Button signUpButton1;
    @FXML
    private Button logInButton1;
    private PawCare pawCare;
    private LoginClassCredentials loginCredentials;

    private static final Logger LOGGER = Logger.getLogger(HelloController.class.getName());

    public HelloController() {}

    @FXML
    private void initialize() {
        signUpButton.setOnAction(event -> openSignUpPage());
        logInButton.setOnAction(event -> openLogInPage());
        signUpButton1.setOnAction(event -> openSignUpPage());
        logInButton1.setOnAction(event -> openLogInPage());
    }
    public void setSharedData(PawCare pawCare, LoginClassCredentials loginCredentials) {
        this.pawCare = pawCare;
        this.loginCredentials = loginCredentials;
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