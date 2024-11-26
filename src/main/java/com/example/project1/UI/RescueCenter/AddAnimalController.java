package com.example.project1.UI.RescueCenter;
import com.example.project1.BLL.LoginClassCredentials;
import com.example.project1.BLL.PawCare;
import com.example.project1.BLL.RequiresSharedData;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.scene.image.Image;

import javafx.scene.control.Alert;

import java.io.File;

import static java.lang.Integer.parseInt;

public class AddAnimalController extends RescueCenterMenuController implements RequiresSharedData {
    @FXML private TextField nameField;
    @FXML private TextField typeField;
    @FXML private TextField breedField;
    @FXML private TextField colorField;
    @FXML private TextField temperatureField;
    @FXML private TextField heartRateField;
    @FXML private TextField respiratoryRateField;
    @FXML private TextField capillaryRefillTimeField;
    @FXML private TextField bloodOxygenLevelField;
    @FXML private TextField bloodGlucoseLevelField;
    @FXML private TextField weightField;

    @FXML private Button uploadButton;
    @FXML private Button submitButton;
    @FXML private Label uploadLabel;
    private PawCare pawCare;
    private LoginClassCredentials loginCredentials;
    private Image animalImage;

    public void initialize() {
        super.initialize();

    }
    public void start(){
        submitButton.setOnAction(event -> handleSubmitButtonAction());
        uploadButton.setOnAction(event -> handleUploadButtonAction());
    }
    public void setSharedData(PawCare pawCare, LoginClassCredentials loginCredentials) {
        if (pawCare == null || loginCredentials == null) {
            System.out.println("Error: Shared data is null.");
        } else {
            this.pawCare = pawCare;
            this.loginCredentials = loginCredentials;
            System.out.println("Shared data set: " + pawCare + ", " + loginCredentials);
            start();
        }
    }

    @FXML
    private void handleSubmitButtonAction() {

        String name = nameField.getText();
        String type = typeField.getText();
        String breed = breedField.getText();
        String color = colorField.getText();
        double temperature = -1;
        double weight = -1;
        int heartRate = -1;
        int respiratoryRate = -1;
        int capillaryRefillTime = -1;
        int bloodOxygenLevel = -1;
        int bloodGlucoseLevel = -1;

        try {
            temperature = Double.parseDouble(temperatureField.getText());
            weight = Double.parseDouble(weightField.getText());
            heartRate = Integer.parseInt(heartRateField.getText());
            respiratoryRate = Integer.parseInt(respiratoryRateField.getText());
            capillaryRefillTime = Integer.parseInt(capillaryRefillTimeField.getText());
            bloodOxygenLevel = Integer.parseInt(bloodOxygenLevelField.getText());
            bloodGlucoseLevel = Integer.parseInt(bloodGlucoseLevelField.getText());
        } catch (NumberFormatException e) {
            showAlert("Please enter valid numbers in all fields.",true);
            return;
        }

        if (name.isEmpty() || type.isEmpty() || breed.isEmpty() || color.isEmpty() ||
                temperature == -1 || heartRate == -1 || respiratoryRate == -1 ||
                capillaryRefillTime == -1 || bloodOxygenLevel == -1 || bloodGlucoseLevel == -1 ||
                weight == -1  || animalImage==null) {
            showAlert("Please fill in all the fields and upload an image.",true);

        }
        else {

            boolean check=pawCare.addAnimalProf(name,type,breed,color,animalImage,temperature,heartRate,respiratoryRate,capillaryRefillTime,bloodOxygenLevel,bloodGlucoseLevel,weight,
                    loginCredentials.getUsername());
            if (check) {
                showAlert("Animal added successfully", false);  // Show success alert
            }
            else {
                showAlert("Unsuccessful", true);
            }
        }

    }
    @FXML
    private void handleUploadButtonAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.gif"));

        File selectedFile = fileChooser.showOpenDialog(uploadButton.getScene().getWindow());

        if (selectedFile != null) {
            animalImage = new Image(selectedFile.toURI().toString());
            uploadLabel.setText("Selected file: " + selectedFile.getName());
        } else {
            uploadLabel.setText("No file selected");
        }
    }
    private void showAlert(String message, boolean isError) {
        Alert.AlertType alertType = isError ? Alert.AlertType.ERROR : Alert.AlertType.INFORMATION;

        Alert alert = new Alert(alertType);
        alert.setTitle(isError ? "Input Error" : "Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
