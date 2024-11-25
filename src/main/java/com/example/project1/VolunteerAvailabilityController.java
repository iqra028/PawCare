
package com.example.project1;
import com.example.project1.BLL.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
