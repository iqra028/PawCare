package com.example.project1.UI.Vet;
import com.example.project1.BLL.LoginClassCredentials;
import com.example.project1.BLL.PawCare;
import com.example.project1.BLL.RequiresSharedData;
import com.example.project1.HelloApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;

public class VetSignUpController implements RequiresSharedData {

    private PawCare pawCare;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField namefield;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField LocationField;
    @FXML
    private TextField PhoneField;

    @FXML
    private Button signUpButton;
    @FXML
    private Button logInButton;
    @FXML
    private Button Finish;
    private LoginClassCredentials loginCredentials;

    @FXML
    private void initialize() {
        signUpButton.setOnAction(event -> openSignUpPage());
        logInButton.setOnAction(event -> openLogInPage());
        Finish.setOnAction(event ->  {
            if (handleSignUp()) {
                openUserHomePage();
            }
        });

    }
    public void start(){
        signUpButton.setOnAction(event -> openSignUpPage());
        logInButton.setOnAction(event -> openLogInPage());
        Finish.setOnAction(event ->  {
            if (handleSignUp()) {
                openUserHomePage();
            }
        });
    }
    public void setSharedData(PawCare pawCare, LoginClassCredentials loginCredentials) {
        if (pawCare == null || loginCredentials == null) {
            System.out.println("Error: Shared data is null.");
        } else {
            this.pawCare = pawCare;
            this.loginCredentials = loginCredentials;
            System.out.println("Shared data set: " + pawCare + ", " + loginCredentials);
            start();
        }
    }
    private void openUserHomePage() {
        loginCredentials.setUsername(usernameField.getText());
        loginCredentials.setPassword(passwordField.getText());
        loginCredentials.setType("vet");
        try {
            HelloApplication.getInstance().changeScene("VetHomeScreen.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean handleSignUp() {
        String username = usernameField.getText();
        String name = namefield.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String Location = LocationField.getText();
        String PhoneNumber = PhoneField.getText();

        if (!username.isEmpty() && !name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !Location.isEmpty() && !PhoneNumber.isEmpty()) {
            boolean reg= pawCare.registerVet(username,name,email,password,Location,PhoneNumber);
            if(reg){
                showAlert("Success", "Sign-up successful!");
                return true;
            }
            else {
                showAlert("Failure", "Username or email already used!");
            }
        } else {
            showAlert("Error", "All fields are required.");
        }
        return false;
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
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
