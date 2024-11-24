package com.example.project1;

import com.example.project1.BLL.*;
import com.example.project1.BLL.injuryReport;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

import java.io.IOException;

public class DisplayInjuryReportforRescueCentercontroller extends RescueCenterMenuController implements RequiresSharedData {

    private PawCare pawCare;
    private LoginClassCredentials loginCredentials;

    @FXML
    private Label reportId;
    @FXML
    private Label vetId;
    @FXML
    private Label rescueCenterId;
    @FXML
    private Label animalId;
    @FXML
    private Label description;
    @FXML
    private Label temperature;
    @FXML
    private Label heartRate;
    @FXML
    private Label respiratoryRate;
    @FXML
    private Label capillaryRefillTime;
    @FXML
    private Label bloodOxygenLevel;
    @FXML
    private Label bloodGlucoseLevel;
    @FXML
    private Label weight;

    @FXML
    private Button goBackButton;

    @Override
    public void initialize() {
        super.initialize();
    }

    @Override
    public void setSharedData(PawCare pawCare, LoginClassCredentials loginCredentials) {
        if (pawCare == null || loginCredentials == null) {
            System.err.println("Error: Shared data is null.");
            return;
        }

        this.pawCare = pawCare;
        this.loginCredentials = loginCredentials;

        System.out.println("Shared data set successfully.");
        start();
    }

    public void start() {
        injuryReport injuryReport=pawCare.retreivereport(SharedProfile.getInstance().getSelectedAnimalProfile(), loginCredentials.getUsername());

        if (injuryReport != null) {
            // Set the attributes to the FXML labels
           // reportId.setText("Report ID: " + injuryReport.getReportid());
            vetId.setText("Vet Name: " + pawCare.getVetname(injuryReport.getVetid()));
            rescueCenterId.setText("Rescue Center Name: " + pawCare.getRescuecentername(injuryReport.getRescuecenterid()));
            //animalId.setText("Animal ID: " + injuryReport.getAnimal_id());
            description.setText("Description: " + injuryReport.getDescription());
            temperature.setText("Temperature: " + injuryReport.getTemperature() + " °C");
            heartRate.setText("Heart Rate: " + injuryReport.getHeartRate() + " bpm");
            respiratoryRate.setText("Respiratory Rate: " + injuryReport.getRespiratoryRate() + " breaths/min");
            capillaryRefillTime.setText("Capillary Refill Time: " + injuryReport.getCapillaryRefillTime() + " seconds");
            bloodOxygenLevel.setText("Blood Oxygen Level: " + injuryReport.getBloodOxygenLevel() + " %");
            bloodGlucoseLevel.setText("Blood Glucose Level: " + injuryReport.getBloodGlucoseLevel() + " mg/dL");
            weight.setText("Weight: " + injuryReport.getWeight() + " kg");
        } else {
            System.out.println("No injury report found for this rescue center.");
        }
    }

    // Add functionality for the "Go Back" button if needed
    @FXML
    public void goBack() {
        try {
            HelloApplication.getInstance().changeScene("AnimalProfileAvailableAnimals.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
