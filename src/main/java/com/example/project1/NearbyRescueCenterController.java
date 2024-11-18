
package com.example.project1;

import com.example.project1.BLL.PawCare;
import com.example.project1.BLL.SharedData;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.List;

public class NearbyRescueCenterController extends UserMenuController{


    @FXML
    private Button FirstAid;

    @FXML
    private VBox rescueCentersContainer;

    @FXML
    private Pane btnRescueCenters1;

    @FXML
    private Label PaneLabel;

    @FXML
    private Button Panebutton;
    PawCare pawCare;

    @FXML
    public void initialize() {
        try {
            super.initialize();
            if (rescueCentersContainer == null) {
                throw new IllegalStateException("rescueCentersContainer is not injected.");
            }

            pawCare = new PawCare(); // Ensure PawCare instance is initialized
            List<String> shelterInfoList = pawCare.fetchNearbyRegisteredRescueCenters(); // Use the new method

            for (String info : shelterInfoList) {
                addRescueCenterPane(info);
            }

            FirstAid.setOnAction(event -> handleFirstAid());
            Panebutton.setOnAction(event -> handleSubmitRequest());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void handleFirstAid() {
    }

    private void handleSubmitRequest() {
        // Get the user's current location (latitude and longitude)
        double[] userLocation = SharedData.getInstance().getLocation(); // Assuming this returns [latitude, longitude]

        // Capture the animal details from the input fields
        String animalType = SharedData.getInstance().getAnimalType();
        String breed = SharedData.getInstance().getBreed();
        String injuryDesc = SharedData.getInstance().getInjuryDesc();
        String imagePath = null;
        System.out.println("heeeeeeeeeeeee");
        pawCare.createAlert(animalType, breed, injuryDesc, imagePath, userLocation);
    }

    private void addRescueCenterPane(String shelterInfo) {
        String[] lines = shelterInfo.split("\n");
        String name = lines[0].replace("Name: ", "").trim();
        String location = lines[1].replace("Location: ", "").trim();
        String phone = lines[2].replace("Phone: ", "").trim();
        String website = lines[3].replace("Website: ", "").trim();
        Pane newPane = new Pane();
        newPane.setPrefHeight(118);
        newPane.setPrefWidth(686);
        newPane.setStyle("-fx-background-color: #EEB673;");
        newPane.setEffect(new DropShadow());
        Label nameLabel = new Label(name);
        nameLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        nameLabel.setLayoutX(25);
        nameLabel.setLayoutY(18);
        Label locationLabel = new Label("Location: " + location);
        locationLabel.setStyle("-fx-font-size: 14px;");
        locationLabel.setLayoutX(25);
        locationLabel.setLayoutY(50);
        Label phoneLabel = new Label("Phone: " + phone);
        phoneLabel.setStyle("-fx-font-size: 14px;");
        phoneLabel.setLayoutX(25);
        phoneLabel.setLayoutY(70);
        Label websiteLabel = new Label("Website: " + website);
        websiteLabel.setStyle("-fx-font-size: 14px;");
        websiteLabel.setLayoutX(25);
        websiteLabel.setLayoutY(90);
        Button submitButton = new Button("Submit");
        submitButton.setStyle("-fx-background-color: #D08122; -fx-text-fill: white; -fx-font-size: 14px;");
        submitButton.setLayoutX(574);
        submitButton.setLayoutY(70);
        submitButton.setPrefSize(98, 34);


        newPane.getChildren().addAll(nameLabel, locationLabel, phoneLabel, websiteLabel, submitButton);
        rescueCentersContainer.getChildren().add(newPane);
    }
}


