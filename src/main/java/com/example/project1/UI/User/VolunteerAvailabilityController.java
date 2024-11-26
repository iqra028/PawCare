
package com.example.project1.UI.User;
import com.example.project1.BLL.*;
import com.example.project1.HelloApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class VolunteerAvailabilityController extends UserMenuController implements RequiresSharedData {
    @FXML
    private Button btnUploadImage;
    private PawCare pawCare;
    private LoginClassCredentials loginCredentials;

    @FXML
    public void initialize() {
        super.initialize();

    }
    public void start()
    {
        btnUploadImage.setOnAction(event -> handleSubmitAction());
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

    @FXML
    private void handleSubmitAction() {
        pawCare.setVolunteerAvailability(true,pawCare.getUserIDByUsername(loginCredentials.getUsername()));
        try {
            HelloApplication.getInstance().changeScene("VolunteerRequests.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
