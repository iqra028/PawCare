package com.example.project1;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class VolunteerController extends UserMenuController {

    @FXML
    private TextField RegField;
    @FXML
    private TextField cnicField;
    @FXML
    private TextField vehicleTypeField;
    @FXML
    private TextField modelField;
    @FXML
    private Button submitButton;

    @FXML
    public void initialize() {
        super.initialize();

        // submitButton.setOnAction(event -> onSubmitClick(event));
    }

    @FXML
    private void handleSubmitAction() {
        String licenseRegistrationNumber = RegField.getText();
        String cnic = cnicField.getText();
        String vehicleType = vehicleTypeField.getText();
        String model = modelField.getText();

        if (licenseRegistrationNumber.isEmpty() || cnic.isEmpty() || vehicleType.isEmpty() || model.isEmpty()) {
            System.out.println("License Registration Number and CNIC are required.");
            return;
        }

        RegField.clear();
        cnicField.clear();
        vehicleTypeField.clear();
        modelField.clear();
    }
}

