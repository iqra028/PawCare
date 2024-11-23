package com.example.project1;

import com.example.project1.BLL.LoginClassCredentials;
import com.example.project1.BLL.PawCare;
import com.example.project1.BLL.RequiresSharedData;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RescueCentreHSController extends RescueCenterMenuController implements RequiresSharedData {

    @FXML
    private Button SeeAlerts;
    @FXML
    private Button ContactVet1;
    private PawCare pawCare;
    private LoginClassCredentials loginCredentials;

    private static final Logger LOGGER = Logger.getLogger(RescueCentreHSController.class.getName());

    @FXML
    public void initialize() {
        super.initialize();

    }
    void start(){
        SeeAlerts.setOnAction(event -> handleSeeAlerts());
        ContactVet1.setOnAction(event -> handleContactVet());
    }

    public void setSharedData(com.example.project1.BLL.PawCare pawCare, LoginClassCredentials loginCredentials) {
        if (pawCare == null || loginCredentials == null) {
            System.out.println("Error: Shared data is null.");
        } else {
            this.pawCare = pawCare;
            this.loginCredentials = loginCredentials;
            System.out.println("Shared data set: " + pawCare + ", " + loginCredentials);
            start();
        }
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
            HelloApplication.getInstance().changeScene("ContactVetRegisteredVet.fxml");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to change scene to Select-UserType.fxml", e);
        }
    }

}
