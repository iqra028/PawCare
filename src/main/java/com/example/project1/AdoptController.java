package com.example.project1;

import com.example.project1.BLL.LoginClassCredentials;
import com.example.project1.BLL.PawCare;
import com.example.project1.BLL.RequiresSharedData;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AdoptController extends UserMenuController implements RequiresSharedData {

    @FXML
    private com.example.project1.BLL.PawCare pawCare;
    private LoginClassCredentials loginCredentials;
    public void initialize() {
        super.initialize();
    }
    void start(){

    }
    public void setSharedData(PawCare pawCare, LoginClassCredentials loginCredentials) {
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
