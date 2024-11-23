package com.example.project1;

import com.example.project1.BLL.LoginClassCredentials;
import com.example.project1.BLL.PawCare;
import com.example.project1.BLL.RequiresSharedData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GenInjuryReportController extends VetMenu implements RequiresSharedData {

    private static final Logger LOGGER = Logger.getLogger(com.example.project1.GenInjuryReportController.class.getName());
    private PawCare pawCare;
    private LoginClassCredentials loginCredentials;
    @FXML
    public void initialize() {
        super.initialize();

    }
    void start(){

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
}
