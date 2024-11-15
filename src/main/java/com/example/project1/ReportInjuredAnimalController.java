package com.example.project1;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import com.example.project1.HomeController;


public class ReportInjuredAnimalController extends HomeController
{
    @FXML
    private Button btnSubmit;

    @FXML
    private TextField txtAnimalType;

    @FXML
    private TextField txtAnimalName;

    @FXML
    private TextField txtBreed;

    @FXML
    private TextField txtColor;

    @FXML
    private TextField txtGender;

    @FXML
    private TextField txtLocation;

    @FXML
    public void initialize() {
        super.initialize();
    }

    @FXML
    private void onSubmitClick(MouseEvent event) {
        // Retrieve values from the text fields
        String animalType = txtAnimalType.getText();
        String animalName = txtAnimalName.getText();
        String breed = txtBreed.getText();
        String color = txtColor.getText();
        String gender = txtGender.getText();
        String location = txtLocation.getText();

        // Validate inputs
        if (animalType.isEmpty() || animalName.isEmpty() || breed.isEmpty() || color.isEmpty() || gender.isEmpty() || location.isEmpty()) {
            showAlert("Error", "Missing Fields", "Please fill out all fields before submitting.");
        } else {
            System.out.println("Submitting Report Missing Animal form");
            System.out.printf("Animal Type: %s, Name: %s, Breed: %s, Color: %s, Gender: %s, Location: %s%n",
                    animalType, animalName, breed, color, gender, location);
            showAlert("Success", "Submission Complete", "Your missing animal report has been submitted successfully.");
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
