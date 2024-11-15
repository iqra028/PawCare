package com.example.project1;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class HomeController {
    @FXML
    private Button btnReportInjuredAnimalHeader;
    @FXML
    private Button btnReportMissingAnimal;
    //    @FXML
//    private Button btnAdopt;
    @FXML
    private Button btnVolunteer;
    @FXML
    private Button btnDonate;
    @FXML
    private Button btnReportInjuredAnimalMain;
    @FXML
    private Button btnDonateMain;

    @FXML
    public void initialize() {
        btnReportInjuredAnimalHeader.setOnAction(event -> handleReportInjuredAnimalHeader());
        btnReportMissingAnimal.setOnAction(event -> handleReportMissingAnimal());
        // btnAdopt.setOnAction(event -> openSignUpPage());
        btnVolunteer.setOnAction(event -> handleVolunteer());
        btnDonate.setOnAction(event -> handleDonate());

    }

    @FXML
    private void handleReportInjuredAnimalHeader() {
        try {
            HelloApplication.getInstance().changeScene("ReportInjuredAnimal.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleReportMissingAnimal() {
        try {
            HelloApplication.getInstance().changeScene("ReportMissingAnimal.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    @FXML
//    private void handleAdopt() {
//
//    }

    @FXML
    private void handleVolunteer() {
        try {
            HelloApplication.getInstance().changeScene("Volunteer.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDonate() {
        try {
            HelloApplication.getInstance().changeScene("DonateForm.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleReportInjuredAnimalMain() throws IOException {

        HelloApplication.getInstance().changeScene("ReportInjuredAnimal.fxml");

    }

    @FXML
    private void handleDonateMain() throws IOException {

        HelloApplication.getInstance().changeScene("DonateForm.fxml");

    }

}
