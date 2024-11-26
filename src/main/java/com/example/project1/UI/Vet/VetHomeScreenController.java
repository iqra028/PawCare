package com.example.project1.UI.Vet;

import com.example.project1.HelloApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class VetHomeScreenController extends VetMenu {

    @FXML
    private Button btnGenerateInjuryRep;
    @FXML
    private Button BtnRequests;

    private static final Logger LOGGER = Logger.getLogger(VetHomeScreenController.class.getName());

    @FXML
    public void initialize() {
        super.initialize();
        btnGenerateInjuryRep.setOnAction(event -> handleGenerateInjuryReportButtonAction());
        BtnRequests.setOnAction(event -> handleBtnRequestsButtonAction());
    }

    @FXML
    private void handleGenerateInjuryReportButtonAction() {
        try {
            HelloApplication.getInstance().changeScene("GenerateInjurtReport.fxml");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to change scene to GenerateInjuryReport.fxml", e);
        }
    }

    @FXML
    private void handleBtnRequestsButtonAction() {
        try {
            HelloApplication.getInstance().changeScene("RequestsFromRescueCenter.fxml");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to change scene to RequestsFromRescueCenter.fxml", e);
        }
    }

}
