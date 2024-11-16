package com.example.project1;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.IOException;


public class ReportInjuredAnimalController extends UserMenuController
{
    @FXML
    private Button btnSubmit;
    @FXML
    private TextField AnimalType;
    @FXML
    private TextField Breed;
    @FXML
    private TextField InjuryDesc;


    private static final Logger LOGGER = Logger.getLogger(com.example.project1.ReportInjuredAnimalController.class.getName());

    @FXML
    public void initialize() {
        super.initialize();
        btnSubmit.setOnAction(event -> onSubmitClick());

    }
    @FXML
    private void NextPage(){
        try {
            HelloApplication.getInstance().changeScene("NearbyRescueCenters.fxml");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to change scene to Select-UserType.fxml", e);
        }
    }

    @FXML
    private void onSubmitClick() {
        String animalType = AnimalType.getText();
        String breed = Breed.getText();
        String InjuryDescription = InjuryDesc.getText();


        if (animalType.isEmpty() || InjuryDescription.isEmpty() || breed.isEmpty()) {
            showAlert("Error", "Missing Fields", "Please fill out all fields before submitting.");
        } else {
            NextPage();
        }
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
