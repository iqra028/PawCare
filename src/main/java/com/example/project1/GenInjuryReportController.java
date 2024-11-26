package com.example.project1;

import com.example.project1.BLL.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class GenInjuryReportController extends VetMenu implements RequiresSharedData {

    private PawCare pawCare;
    private LoginClassCredentials loginCredentials;

    // FXML elements linked to FXML file
    @FXML
    private TextField temperatureField;
    @FXML
    private TextField heartRateField;
    @FXML
    private TextField respiratoryRateField;
    @FXML
    private TextField capillaryRefillField;
    @FXML
    private TextField bloodOxygenField;
    @FXML
    private TextField bloodGlucoseField;
    @FXML
    private TextField weightField;
    @FXML
    private TextField weightField1; // This seems to be for the description
    @FXML
    private Button updateProfileButton;
    @FXML
    private Button updateProfileButton1;

    private Profile selectedProfile;
    private injuryReport re;
    // Initializes the controller
    @FXML
    public void initialize() {
        super.initialize();
    }

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

    /**
     * Saves the injury report data
     */
    public void start(){
        selectedProfile=SharedProfile.getInstance().getSelectedAnimalProfile();
        System.out.println(pawCare.getRescuecenteridthroughanimalid(selectedProfile));

       updateProfileButton.setOnAction(e -> {save();});
        updateProfileButton1.setOnAction(e->{sendAnimaltoRescuecenter(re);
        });



    }
    private void sendAnimaltoRescuecenter(injuryReport report){
        pawCare.visitedvet(report);
    }
    private void save() {
        try {
            // Validate description
            String description = weightField1.getText();
            if (description.isEmpty()) {
                throw new IllegalArgumentException("Description cannot be empty.");
            }

            // Validate and parse other inputs
            double temperature = parseDouble(temperatureField.getText(), "Temperature");
            int heartRate = parseInt(heartRateField.getText(), "Heart Rate");
            int respiratoryRate = parseInt(respiratoryRateField.getText(), "Respiratory Rate");
            int capillaryRefillTime = parseInt(capillaryRefillField.getText(), "Capillary Refill Time");
            int bloodOxygenLevel = parseInt(bloodOxygenField.getText(), "Blood Oxygen Level");
            int bloodGlucoseLevel = parseInt(bloodGlucoseField.getText(), "Blood Glucose Level");
            double weight = parseDouble(weightField.getText(), "Weight");

            // Create injury report
            injuryReport report = new injuryReport();


            String vetId = pawCare.getVetfromUsername(loginCredentials.getUsername()).getVetID();
            if (vetId == null || vetId.isEmpty()) {
                throw new IllegalArgumentException("Vet ID cannot be null or empty.");
            }
            else{
                report.setVetid(vetId);
            }



            // Validate and set Rescue Center ID


            if (SharedProfile.getInstance().getSelectedAnimalProfile() == null ){//|| SharedProfile.getInstance().getSelectedAnimalProfile().getRescueCenterId().isEmpty()) {
                throw new IllegalArgumentException("Rescue Center ID cannot be null or empty.");
            }
            else {
                report.setRescuecenterid(SharedProfile.getInstance().getSelectedAnimalProfile().getRescueCenterId());
            }

            // Validate and set Animal ID
            String animalId = selectedProfile.getAnimal().getAnimalID().toString();
            if (animalId == null || animalId.isEmpty()) {
                throw new IllegalArgumentException("Animal ID cannot be null or empty.");
            }
            report.setAnimal_id(animalId);

            // Set other report fields
            report.setDescription(description);
            report.setTemperature(temperature);
            report.setHeartRate(heartRate);
            report.setRespiratoryRate(respiratoryRate);
            report.setCapillaryRefillTime(capillaryRefillTime);
            report.setBloodOxygenLevel(bloodOxygenLevel);
            report.setBloodGlucoseLevel(bloodGlucoseLevel);
            report.setWeight(weight);

            // Log the report details for debugging
            System.out.println(report.hell());

            // Save report
            generateReport(report);
            re=report;

            // Show success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Injury report generated successfully!");
            alert.showAndWait();

        } catch (Exception e) {
            e.printStackTrace(); // Log the stack trace for debugging
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }


    // Utility methods for parsing
    private double parseDouble(String value, String fieldName) throws IllegalArgumentException {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(fieldName + " must be a valid number.");
        }
    }

    private int parseInt(String value, String fieldName) throws IllegalArgumentException {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(fieldName + " must be a valid integer.");
        }
    }

    /**
     * Saves the injury report using PawCare
     *
     * @param report Injury report to save
     */
    private void generateReport(injuryReport report) {
        pawCare.saveInjuryReport(report);
    }
}
