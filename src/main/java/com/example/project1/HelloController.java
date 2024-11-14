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

    public HelloController() {
    }

    @FXML
    private void initialize() {
        this.signUpButton.setOnAction((event) -> {
            this.openSignUpPage();
        });
    }

    private void openSignUpPage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("signup-view.fxml"));
            Scene signUpScene = new Scene((Parent)fxmlLoader.load(), 1466, 800);
            Stage newStage = new Stage();
            newStage.setTitle("Sign up");
            newStage.setScene(signUpScene);
            newStage.show();
        } catch (IOException var4) {
            IOException e = var4;
            e.printStackTrace();
        }

    }
}
