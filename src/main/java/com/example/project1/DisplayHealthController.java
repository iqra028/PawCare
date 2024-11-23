package com.example.project1;

import com.example.project1.BLL.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;

import java.util.List;

public class DisplayHealthController extends VetMenu implements RequiresSharedData {

    @FXML
    private ImageView animalImage;

    @FXML
    private Label nameLabel;

    @FXML
    private Label typeLabel;

    @FXML
    private Label breedLabel;

    @FXML
    private Label colorLabel;

    @FXML
    private Label visitedVetLabel;

    @FXML
    private Label withVetLabel;

    @FXML
    private Label healthStatusLabel;

    @FXML
    private Label temperatureLabel;

    @FXML
    private Label heartRateLabel;

    @FXML
    private Label respiratoryRateLabel;

    @FXML
    private Button goBackButton;

    private PawCare pawCare;
    private LoginClassCredentials loginCredentials;

    // Method to set shared data (PawCare and LoginCredentials)
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

    // Start the display process
    public void start() {
        displayAnimals();

        // Set up the Go Back button
        goBackButton.setStyle("-fx-background-color: #D08122; -fx-text-fill: white; -fx-padding: 10 20; -fx-font-size: 14px;");
        goBackButton.setText("Go Back");
        goBackButton.setOnAction(event -> goBack());
    }

    // Method to fetch the animals associated with the vet (replace with actual logic)
    private List<Profile> getAnimalsForVet() {
        Vets vet = pawCare.getVetfromUsername(loginCredentials.getUsername());
        return vet.getCurrentlybeingchecked();
    }

    // Method to display the list of animals for the vet
    public void displayAnimals() {
        // Get the selected animal profile
        Profile animalProfile = SharedProfile.getInstance().getSelectedAnimalProfile();
        Animal animal = animalProfile.getAnimal();

        // Animal Image
        animalImage.setImage(animal.getImage());

        // Animal Details
        nameLabel.setText("Name: " + animal.getName());
        typeLabel.setText("Type: " + animal.getType());
        breedLabel.setText("Breed: " + animal.getBreed());
        colorLabel.setText("Color: " + animal.getColor());
        visitedVetLabel.setText("Visited Vet: " + (animal.isVisitedVet() ? "Yes" : "No"));
        withVetLabel.setText("With Vet: " + (animal.isWithVet() ? "Yes" : "No"));

        // Get the health data
        HealthDescription health = animal.getHealth();

        // Health Status
        healthStatusLabel.setText("Health Status: " + (health != null && animal.isHealthStatus() ? "Healthy" : "Needs Attention"));

        // Health Information
        temperatureLabel.setText("Temperature: " + (health != null ? health.getTemperature() : "N/A") + " °C");
        heartRateLabel.setText("Heart Rate: " + (health != null ? health.getHeartRate() : "N/A") + " bpm");
        respiratoryRateLabel.setText("Respiratory Rate: " + (health != null ? health.getRespiratoryRate() : "N/A") + " breaths/min");
    }

    // Action to go back (back to previous screen)
    @FXML
    public void goBack() {
        System.out.println("Going back...");
        // Implement the logic to go back, e.g., using a stage transition or scene change.
    }

    // Initialize method from parent class, if any setup is needed
    @FXML
    public void initialize() {
        super.initialize();
    }
}
