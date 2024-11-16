package com.example.project1;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RescueCentreHSController {

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
    private Button SeeAlerts;

    private static final Logger LOGGER = Logger.getLogger(RescueCentreHSController.class.getName());

    @FXML
    private void handleAlertsReceived() {
        try {
            HelloApplication.getInstance().changeScene("ReportInjuredAnimal.fxml");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to change scene to Select-UserType.fxml", e);
        }
    }

    @FXML
    private void handleAnimalProfiles() {
        try {
            HelloApplication.getInstance().changeScene("ReportInjuredAnimal.fxml");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to change scene to Select-UserType.fxml", e);
        }
    }

    @FXML
    private void handleAdoptionRequests() {
        try {
            HelloApplication.getInstance().changeScene("ReportInjuredAnimal.fxml");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to change scene to Select-UserType.fxml", e);
        }
    }

    @FXML
    private void handleContactVet() {
        try {
            HelloApplication.getInstance().changeScene("ReportInjuredAnimal.fxml");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to change scene to Select-UserType.fxml", e);
        }
    }

    @FXML
    private void handleDonationsMade() {
        try {
            HelloApplication.getInstance().changeScene("ReportInjuredAnimal.fxml");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to change scene to Select-UserType.fxml", e);
        }
    }

    @FXML
    private void handleSeeAlerts() {
        try {
            HelloApplication.getInstance().changeScene("ReportInjuredAnimal.fxml");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to change scene to Select-UserType.fxml", e);
        }
    }
}
