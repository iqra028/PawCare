package com.example.project1;

import com.example.project1.BLL.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class VetProfileController extends RescueCenterMenuController implements RequiresSharedData{

    @FXML
    private Label vetName;

    @FXML
    private Label vetLocation;

    @FXML
    private Label vetPhone;

    @FXML
    private Label vetEmail;

    @FXML
    private Button contactButton;

    @FXML
    private Button goBackButton;
    private PawCare pawCare;
    private LoginClassCredentials loginCredentials;
    @FXML
    private ImageView profileImage;

    public void initialize() {
        super.initialize();


    }
    public void start(){
        setVetProfile(SharedData.getInstance().getSelectedVet());
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

    public void setVetProfile(Vets vet) {
        if (vet == null) {
            System.out.println("Error: Vet data is null.");
            return;
        }

        vetName.setText(vet.getName());
        vetLocation.setText("Location: " + vet.getLocation());
        vetPhone.setText("Phone: " + vet.getPhoneNumber());
        vetEmail.setText("Email: " + vet.getEmail());
        goBackButton.setOnAction(event -> {
            try {
                System.out.println("Go back button clicked.");
                HelloApplication.getInstance().changeScene("ContactVetRegisteredVet.fxml");

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
