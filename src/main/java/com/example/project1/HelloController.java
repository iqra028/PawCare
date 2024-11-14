package com.example.project1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloController {

    @FXML
    private Button signUpButton;
    @FXML
    private Button logInButton;

    @FXML
    private void initialize()
    {
        signUpButton.setOnAction(event -> openSignUpPage());

        logInButton.setOnAction(event -> openLogInPage());
    }

    private void openSignUpPage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/com.example.project1/images/signup-view.fxml"));
            Parent signUpRoot = fxmlLoader.load();
            Stage signUpStage = new Stage();
            signUpStage.setTitle("Sign Up");
            signUpStage.setScene(new Scene(signUpRoot, 1366, 768));
            signUpStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openLogInPage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/com.example.project1/images/login-view.fxml"));
            Parent logInRoot = fxmlLoader.load();
            Stage logInStage = new Stage();
            logInStage.setTitle("Login");
            logInStage.setScene(new Scene(logInRoot, 1366, 768));
            logInStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
