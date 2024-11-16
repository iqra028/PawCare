package com.example.project1;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Label;

public class UserHomeScreenController extends UserMenuController {

    @FXML
    private Button btnReportInjuredAnimalMain;
    @FXML
    private Button btnDonateMain;

    private static final Logger LOGGER = Logger.getLogger(UserHomeScreenController.class.getName());

    @FXML
    public void initialize() {
        super.initialize();

        btnReportInjuredAnimalMain.setOnAction(event -> handleReportInjuredAnimalMain());
        btnDonateMain.setOnAction(event -> handleDonateMain());

    }

    @FXML
    private void handleReportInjuredAnimalMain() {
        try {
            HelloApplication.getInstance().changeScene("ReportInjuredAnimal.fxml");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to change scene to Select-UserType.fxml", e);
        }
    }

    @FXML
    private void handleDonateMain()  {
        try {
            HelloApplication.getInstance().changeScene("DonateForm.fxml");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to change scene to Select-UserType.fxml", e);
        }
    }

}
