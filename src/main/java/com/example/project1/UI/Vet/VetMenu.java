package com.example.project1.UI.Vet;

import com.example.project1.HelloApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VetMenu {


    @FXML
    private Label PawCare;
    @FXML
    private Button InjuryReport;
    @FXML
    private Button Requests;
//    @FXML
//    private Button btnGenerateInjuryRep;
//    @FXML
//    private Button BtnRequests;

    private static final Logger LOGGER = Logger.getLogger(VetMenu.class.getName());

    @FXML
    public void initialize() {
        PawCare.setOnMouseClicked(event -> handleLogo());
        InjuryReport.setOnAction(event -> handleInjuryReportButtonAction());
        Requests.setOnAction(event -> handleRequestsButtonAction());

    }

    @FXML
    private void handleLogo() {
        try {
            HelloApplication.getInstance().changeScene("VetHomeScreen.fxml");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to change scene to Select-UserType.fxml", e);
        }
    }

    @FXML
    private void handleInjuryReportButtonAction() {
        try {
            HelloApplication.getInstance().changeScene("AnimalProfilesForVet.fxml");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to change scene to GenerateInjuryReport.fxml", e);
        }
    }

    @FXML
    private void handleRequestsButtonAction() {
        try {
            HelloApplication.getInstance().changeScene("RequestsFromRescueCenter.fxml");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to change scene to RequestsFromRescueCenter.fxml", e);
        }
    }


}
