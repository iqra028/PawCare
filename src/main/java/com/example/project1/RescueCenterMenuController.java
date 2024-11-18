package com.example.project1;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RescueCenterMenuController {

    @FXML
    private Button AlertsReceived;
    @FXML
    private Button AnimalProfiles;
    @FXML
    private Button AdoptionRequests;
    @FXML
    private Button ContactVet;
    @FXML
    private Button DonationsMade;
    @FXML
    private Label PawCare;

    private static final Logger LOGGER = Logger.getLogger(com.example.project1.RescueCentreHSController.class.getName());

    @FXML
    public void initialize() {

        PawCare.setOnMouseClicked(event -> handleLogo());
        AlertsReceived.setOnAction(event -> handleAlertsReceived());
        AnimalProfiles.setOnAction(event -> handleAnimalProfiles());
        AdoptionRequests.setOnAction(event -> handleAdoptionRequests());
        ContactVet.setOnAction(event -> handleContactVet());
        DonationsMade.setOnAction(event -> handleDonationsMade());
    }

    @FXML
    private void handleLogo() {
        try {
            HelloApplication.getInstance().changeScene("RescueCenterHomeScreen.fxml");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to change scene to Select-UserType.fxml", e);
        }
    }

    @FXML
    private void handleAlertsReceived() {
        try {
            HelloApplication.getInstance().changeScene("AlertsReceived.fxml");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to change scene to Select-UserType.fxml", e);
        }
    }

    @FXML
    private void handleAnimalProfiles() {
        try {
            HelloApplication.getInstance().changeScene("AnimalProfile.fxml");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to change scene to Select-UserType.fxml", e);
        }
    }

    @FXML
    private void handleAdoptionRequests() {
        try {
            HelloApplication.getInstance().changeScene("AdoptionRequests.fxml");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to change scene to Select-UserType.fxml", e);
        }
    }

    @FXML
    private void handleContactVet() {
        try {
            HelloApplication.getInstance().changeScene("ContactVet.fxml");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to change scene to Select-UserType.fxml", e);
        }
    }

    @FXML
    private void handleDonationsMade() {
        try {
            HelloApplication.getInstance().changeScene("DonationRequests.fxml");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to change scene to Select-UserType.fxml", e);
        }
    }

}


