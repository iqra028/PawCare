package com.example.project1.UI.RescueCenter;

import com.example.project1.BLL.LoginClassCredentials;
import com.example.project1.BLL.PawCare;
import com.example.project1.BLL.RequiresSharedData;
import javafx.fxml.FXML;

public class AnimalProfilesController  extends RescueCenterMenuController implements RequiresSharedData {
    private PawCare pawCare;
    private LoginClassCredentials loginCredentials;
    @FXML
    public void initialize() {
        super.initialize();
    }
    public void start(){

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
