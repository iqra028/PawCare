package com.example.project1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
public class HelloApplication extends Application {
    private static HelloApplication instance;
    private Stage stage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        instance = this;
        this.stage = primaryStage;
        changeScene("hello-view.fxml");
    }

    public static HelloApplication getInstance() {
        return instance;
    }

    public void changeScene(String fxmlFile) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = fxmlLoader.load();
        stage.setScene(new Scene(root, 1466, 800));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
