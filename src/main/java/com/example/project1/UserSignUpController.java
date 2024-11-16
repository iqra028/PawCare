package com.example.project1;


import com.example.project1.BLL.PawCare;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import java.io.IOException;

public class UserSignUpController {

    private PawCare pawCare;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField genderField;
    @FXML
    private Button signUpButton;
    @FXML
    private Button logInButton;
    @FXML
    private Button Finish;

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
    private void openUserHomePage() {
        try {
            HelloApplication.getInstance().changeScene("UserHomeScreen.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean handleSignUp()
    {
        pawCare=new PawCare();
        String username = usernameField.getText();
        String name = nameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String gender = genderField.getText();

        if (!username.isEmpty() && !email.isEmpty() && !password.isEmpty() && !gender.isEmpty() && !name.isEmpty()) {
            boolean reg= pawCare.registerUser(username,name,email,password,gender);
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
