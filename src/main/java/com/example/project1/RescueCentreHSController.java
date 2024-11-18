package com.example.project1;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RescueCentreHSController extends RescueCenterMenuController {

    @FXML
    private Button SeeAlerts;
    @FXML
    private Button ContactVet1;

    private static final Logger LOGGER = Logger.getLogger(RescueCentreHSController.class.getName());

    @FXML
    public void initialize() {
        super.initialize();
        SeeAlerts.setOnAction(event -> handleSeeAlerts());
        ContactVet1.setOnAction(event -> handleContactVet());
    }

    @FXML
    private void handleSeeAlerts() {
        try {
            HelloApplication.getInstance().changeScene("AlertsReceived.fxml");
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

}
