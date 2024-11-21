package com.example.project1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class AnimalProfilesControllerAvA  extends RescueCenterMenuController{

    @FXML
    private Button addAnimalBtn;
    @FXML
    public void initialize() {

        super.initialize();
        addAnimalBtn.setOnAction(event -> openAddAnimalPage());
    }
    private void openAddAnimalPage() {
        try {
            HelloApplication.getInstance().changeScene("AddAnimal.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
