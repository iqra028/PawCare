package com.example.project1;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import com.example.project1.HomeController;

public class ReportMissingAnimalController extends HomeController{

    @FXML
    private Button btnFoundMissingAnimal;

    @FXML
    private Button btnReportMissingAnimalForm;

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

    @Override
    public void initialize() {
        super.initialize();

       // btnSubmit.setOnAction(event -> onSubmitClick(event));
    }

    @FXML
    private void onSubmitClick(MouseEvent event) {
        String animalType = txtAnimalType.getText();
        String animalName = txtAnimalName.getText();
        String breed = txtBreed.getText();
        String color = txtColor.getText();
        String gender = txtGender.getText();
        String location = txtLocation.getText();

        if (animalType.isEmpty() || animalName.isEmpty() || breed.isEmpty() || color.isEmpty() || gender.isEmpty() || location.isEmpty()) {
            showAlert("Error", "Missing Fields", "Please fill out all fields before submitting.");
        } else {
            showAlert("Success", "Submission Complete", "Your missing animal report has been submitted successfully.");
        }
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
