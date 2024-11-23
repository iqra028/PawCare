package com.example.project1;

import com.example.project1.BLL.LoginClassCredentials;
import com.example.project1.BLL.PawCare;
import com.example.project1.BLL.RequiresSharedData;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LogInController implements RequiresSharedData  {

    private PawCare pawCare; // Shared PawCare instance
    private LoginClassCredentials loginCredentials; // Shared credentials instance
    @FXML
    private TextField emailField;
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

    private boolean loginSuccessful = false; // Tracks if login was successful
    private String homePage = "";
    private String userType = ""; // Tracks the selected user type

    @FXML
    private void initialize() {
        signUpButton.setOnAction(event -> openSignUpPage());
        logInButton.setOnAction(event -> openLogInPage());
        UserLogin.setOnAction(event -> handleUserLogin());
        VetLogin.setOnAction(event -> handleVetLogin());
        RescueCenterLogin.setOnAction(event -> handleRescueCenterLogin());
        Finish.setOnAction(event -> handleFinish());
    }

    @Override
    public void setSharedData(PawCare pawCare, LoginClassCredentials loginCredentials) {
        this.pawCare = pawCare;
        this.loginCredentials = loginCredentials;
    }

    private void handleUserLogin() {
        String username = emailField.getText();
        String password = passwordField.getText();

        if (!username.isEmpty() && !password.isEmpty()) {
            if (pawCare.login(username, password, "user")) {
                loginSuccessful = true;
                userType = "user";
                homePage = "UserHomeScreen.fxml";
                saveCredentials(username, password, userType);
                showAlert("Login Success", "User logged in successfully! Press Finish to continue.");
            } else {
                loginSuccessful = false;
                showAlert("Login Error", "Invalid username or password.");
            }
        } else {
            showAlert("Login Error", "Please enter both username and password.");
        }
    }

    private void handleVetLogin() {
        String username = emailField.getText();
        String password = passwordField.getText();

        if (!username.isEmpty() && !password.isEmpty()) {
            if (pawCare.login(username, password, "vet")) {
                loginSuccessful = true;
                userType = "vet";
                homePage = "VetHomeScreen.fxml";
                saveCredentials(username, password, userType);
                showAlert("Login Success", "Vet logged in successfully! Press Finish to continue.");
            } else {
                loginSuccessful = false;
                showAlert("Login Error", "Invalid username or password.");
            }
        } else {
            showAlert("Login Error", "Please enter both username and password.");
        }
    }

    private void handleRescueCenterLogin() {
        String username = emailField.getText();
        String password = passwordField.getText();

        if (!username.isEmpty() && !password.isEmpty()) {
            if (pawCare.login(username, password, "rescue center")) {
                loginSuccessful = true;
                userType = "rescue center";
                homePage = "RescueCenterHomeScreen.fxml";
                saveCredentials(username, password, userType);
                showAlert("Login Success", "Rescue Center logged in successfully! Press Finish to continue.");
            } else {
                loginSuccessful = false;
                showAlert("Login Error", "Invalid username or password.");
            }
        } else {
            showAlert("Login Error", "Please enter both username and password.");
        }
    }

    private void handleFinish() {
        if (loginSuccessful && !homePage.isEmpty()) {
            openHomePage(homePage);
        } else {
            showAlert("Error", "Login not successful or no user type selected.");
        }
    }

    private void openHomePage(String fxmlFile) {
        try {
            HelloApplication.getInstance().changeScene(fxmlFile);
        } catch (IOException e) {
            e.printStackTrace();
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

    private void saveCredentials(String username, String password, String type) {
        // Save credentials into the shared LoginClassCredentials object
        if (loginCredentials != null) {
            loginCredentials.setUsername(username);
            loginCredentials.setPassword(password);
            loginCredentials.setType(type);
        } else {
            showAlert("Error", "Shared Login Credentials object is not initialized.");
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
