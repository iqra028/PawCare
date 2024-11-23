package com.example.project1;

import com.example.project1.BLL.LoginClassCredentials;
import com.example.project1.BLL.PawCare;
import com.example.project1.BLL.RequiresSharedData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserMenuController  {

    @FXML
    private Button btnReportInjuredAnimalHeader;
    @FXML
    private Button btnReportMissingAnimal;
    @FXML
    private Button btnAdopt;
    @FXML
    private Button btnVolunteer;
    @FXML
    private Button btnDonate;
    @FXML
    private Label PawCare;
    private PawCare pawCare;
    private static final Logger LOGGER = Logger.getLogger(com.example.project1.UserMenuController.class.getName());
    private LoginClassCredentials loginCredentials;

    @FXML
    public void initialize() {
        PawCare.setOnMouseClicked(event -> handleLogo());
        btnReportInjuredAnimalHeader.setOnAction(event -> handleReportInjuredAnimalHeader());
        btnReportMissingAnimal.setOnAction(event -> handleReportMissingAnimal());
        btnAdopt.setOnAction(event -> handleAdopt());

        btnDonate.setOnAction(event -> handleDonate());
        starting();

    }
    public void starting(){
        System.out.println("Volunteer button clicked.");

        btnVolunteer.setOnAction(event -> handleVolunteer());
    }

    @FXML
    private void handleLogo() {
        try {
            HelloApplication.getInstance().changeScene("UserHomeScreen.fxml");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to change scene to Select-UserType.fxml", e);
        }
    }


    @FXML
    private void handleReportInjuredAnimalHeader() {
        try {
            HelloApplication.getInstance().changeScene("ReportInjuredAnimal.fxml");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to change scene to Select-UserType.fxml", e);
        }
    }

    @FXML
    private void handleReportMissingAnimal() {
        try {
            HelloApplication.getInstance().changeScene("ReportMissingAnimal.fxml");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to change scene to Select-UserType.fxml", e);
        }
    }

    @FXML
    private void handleAdopt() {
        try {
            HelloApplication.getInstance().changeScene("AdoptMenu.fxml");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to change scene to Select-UserType.fxml", e);
        }
    }

    @FXML
    private void handleVolunteer() {
        System.out.println("Volunteer");
        try {
            System.out.println("Volunteer");
            //System.out.println(pawCare.ifUserisaVolunter());
            //if (pawCare.ifUserisaVolunter())
            //{
                HelloApplication.getInstance().changeScene("Volunteer.fxml");

            //}
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to change scene to Select-UserType.fxml", e);
        }
    }

    @FXML
    private void handleDonate() {
        try {
            HelloApplication.getInstance().changeScene("DonateForm.fxml");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to change scene to Select-UserType.fxml", e);
        }
    }


}
