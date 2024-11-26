package com.example.project1.UI.User;

import com.example.project1.HelloApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReportFoundAnimalController extends UserMenuController {

    @FXML
    private Button Found;
    @FXML
    private Button Missing;
    @FXML
    private TextField animalTypeField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField breedField;
    @FXML
    private TextField colorField;
    @FXML
    private TextField genderField;
    @FXML
    private Button submitButton;

    private static final Logger LOGGER = Logger.getLogger(ReportFoundAnimalController.class.getName());

    @FXML
    public void initialize() {
        super.initialize();
        Found.setOnAction(event -> handleFoundButtonAction());
        Missing.setOnAction(event -> handleMissingButtonAction());
    }

    @FXML
    private void handleFoundButtonAction() {
        try {
            HelloApplication.getInstance().changeScene("FoundMissingAnimal.fxml");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to change scene to Select-UserType.fxml", e);
        }
    }

    @FXML
    private void handleMissingButtonAction() {
        try {
            HelloApplication.getInstance().changeScene("ReportMissingAnimal.fxml");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to change scene to Select-UserType.fxml", e);
        }
    }

    @FXML
    private void handleSubmitButtonAction() {
        System.out.println("Submit button clicked");
        System.out.println("Animal Type: " + animalTypeField.getText());
        System.out.println("Name: " + nameField.getText());
        System.out.println("Breed: " + breedField.getText());
        System.out.println("Color: " + colorField.getText());
        System.out.println("Gender: " + genderField.getText());
    }
}


