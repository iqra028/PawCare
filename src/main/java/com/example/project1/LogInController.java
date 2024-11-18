package com.example.project1;

import com.example.project1.BLL.PawCare;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;

public class LogInController {

    private PawCare pawCare;
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

    private HelloApplication helloApplication;
    private boolean loginSuccessful = false; // Tracks if login was successful
    private String homePage = "";

    @FXML
    private void initialize() {
        pawCare=new PawCare();
        signUpButton.setOnAction(event -> openSignUpPage());
        logInButton.setOnAction(event -> openLogInPage());
        UserLogin.setOnAction(event -> handleUserLogin());
        VetLogin.setOnAction(event -> handleVetLogin());
        RescueCenterLogin.setOnAction(event -> handleRescueCenterLogin());
        Finish.setOnAction(event -> handleFinish());
    }

    /* private void openUserHomePage() {
         try {
             HelloApplication.getInstance().changeScene("UserHomeScreen.fxml");
         } catch (IOException e) {
             e.printStackTrace();
         }
     }*/

    private void handleUserLogin() {
        String username = emailField.getText();
        String password = passwordField.getText();

        if (!username.isEmpty() && !password.isEmpty()) {
            if (pawCare.login(username, password,"user")) {
                loginSuccessful = true;
                homePage = "UserHomeScreen.fxml";
                showAlert("Login Success", "User logged in successfully! Press Login to continue.");
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
            if (pawCare.login(username, password,"vet")) {
                loginSuccessful = true;
                homePage = "VetHomeScreen.fxml";
                showAlert("Login Success", "Vet logged in successfully! Press Login to continue.");
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
            if (pawCare.login(username, password,"rescue center")) {
                loginSuccessful = true;
                homePage = "RescueCenterHomeScreen.fxml";
                showAlert("Login Success", "Rescue Center logged in successfully! Press Login to continue.");
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

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText((String)null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
