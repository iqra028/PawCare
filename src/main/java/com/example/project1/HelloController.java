//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.project1;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
public class HelloController {
    @FXML
    private Button signUpButton;
    @FXML
    private Button logInButton;
    public HelloController() {}

    @FXML
    private void initialize() {
            signUpButton.setOnAction(event -> openSignUpPage());
            logInButton.setOnAction(event -> openLogInPage());
    }

    private void openSignUpPage() {
        try {
            HelloApplication.getInstance().changeScene("signup-view.fxml");
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
}