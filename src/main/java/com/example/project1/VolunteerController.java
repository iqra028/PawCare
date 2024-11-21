package com.example.project1;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class VolunteerController extends UserMenuController {

    @FXML
    private TextField RegField;
    @FXML
    private TextField cnicField;
    @FXML
    private TextField modelField;
    @FXML
    private VBox vehicleTypeVBox;
    @FXML
    private Label selectedVehicleLabel;
    @FXML
    private Button submitButton;

    // Variable to store the selected vehicle type
    private String selectedVehicleType;

    @FXML
    public void initialize() {
        super.initialize();

        // Set up vehicle type selection
        for (String type : new String[]{"Car", "Motorbike", "Bicycle", "Truck", "Van"}) {
            Text typeText = new Text(type);
            typeText.setStyle("-fx-font-size: 16px; -fx-text-fill: white; -fx-cursor: hand;");
            typeText.setOnMouseClicked(this::handleVehicleTypeClick);
            vehicleTypeVBox.getChildren().add(typeText);
        }

        // Set submit button action
        submitButton.setOnAction(event -> handleSubmitAction());
    }

    @FXML
    private void handleVehicleTypeClick(MouseEvent event) {
        // Get the clicked vehicle type
        Text clickedText = (Text) event.getSource();
        selectedVehicleType = clickedText.getText();
        selectedVehicleLabel.setText("Selected Vehicle Type: " + selectedVehicleType);
        System.out.println("Vehicle type selected: " + selectedVehicleType);
    }

    @FXML
    private void handleSubmitAction() {
        // Get input values
        String licenseRegistrationNumber = RegField.getText();
        String cnic = cnicField.getText();
        String model = modelField.getText();

        // Validation
        if (licenseRegistrationNumber.isEmpty() || cnic.isEmpty() || selectedVehicleType == null || model.isEmpty()) {
            System.out.println("All fields are required.");
            return;
        }

        // Log or process the information
        System.out.println("Submitted details:");
        System.out.println("License Registration Number: " + licenseRegistrationNumber);
        System.out.println("CNIC: " + cnic);
        System.out.println("Vehicle Type: " + selectedVehicleType);
        System.out.println("Model: " + model);

        // Clear the fields
        RegField.clear();
        cnicField.clear();
        modelField.clear();
        selectedVehicleLabel.setText("");
        selectedVehicleType = null;
    }
}
