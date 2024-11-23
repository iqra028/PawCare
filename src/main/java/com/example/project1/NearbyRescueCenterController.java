package com.example.project1;

import com.example.project1.BLL.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.List;

public class NearbyRescueCenterController extends UserMenuController implements RequiresSharedData {

    @FXML
    private Button FirstAid;

    @FXML
    private VBox rescueCentersContainer;

    private LoginClassCredentials loginCredentials;
    @FXML
    private Button Panebutton;

    private PawCare pawCare;

    @FXML
    public void initialize() {

            super.initialize();

    }
    void start(){
        if (rescueCentersContainer == null) {
            throw new IllegalStateException("rescueCentersContainer is not injected.");
        }
        List<RescueCenter> shelterInfoList = pawCare.fetchNearbyRegisteredRescueCenters();

        for (RescueCenter info : shelterInfoList) {
            addRescueCenterPane(info);
        }
        FirstAid.setOnAction(event -> handleFirstAid());
        Panebutton.setOnAction(event -> handleSubmitRequest());
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


    private void handleFirstAid() {
    }

    private void handleSubmitRequest() {

    }

    private void addRescueCenterPane(RescueCenter rescueCenter) {
        Pane newPane = new Pane();
        newPane.setPrefHeight(118);
        newPane.setPrefWidth(686);
        newPane.setStyle("-fx-background-color: #EEB673;");
        newPane.setEffect(new DropShadow());

        Label nameLabel = new Label(rescueCenter.getName());
        nameLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        nameLabel.setLayoutX(25);
        nameLabel.setLayoutY(18);

        Label locationLabel = new Label("Location: " + rescueCenter.getLocation());
        locationLabel.setStyle("-fx-font-size: 14px;");
        locationLabel.setLayoutX(25);
        locationLabel.setLayoutY(50);

        Label phoneLabel = new Label("Phone: " + rescueCenter.getPhoneNumber());
        phoneLabel.setStyle("-fx-font-size: 14px;");
        phoneLabel.setLayoutX(25);
        phoneLabel.setLayoutY(70);

        Label websiteLabel = new Label("Website: " + rescueCenter.getEmail());
        websiteLabel.setStyle("-fx-font-size: 14px;");
        websiteLabel.setLayoutX(25);
        websiteLabel.setLayoutY(90);

        Button submitButton = new Button("Submit");
        submitButton.setStyle("-fx-background-color: #D08122; -fx-text-fill: white; -fx-font-size: 14px;");
        submitButton.setLayoutX(574);
        submitButton.setLayoutY(70);
        submitButton.setPrefSize(98, 34);

        submitButton.setUserData(rescueCenter.getRescueCenterID());
        newPane.getChildren().addAll(nameLabel, locationLabel, phoneLabel, websiteLabel, submitButton);
        rescueCentersContainer.getChildren().add(newPane);
        submitButton.setOnAction(event -> handleSubmitRequest(submitButton));
    }

    private void handleSubmitRequest(Button submitButton) {
        String rescueCenterId = (String) submitButton.getUserData();
        System.out.println("Clicked Rescue Center ID: " + rescueCenterId);
        pawCare.createAlert(SharedData.getInstance().getAnimalType(),SharedData.getInstance().getBreed(),
                SharedData.getInstance().getInjuryDesc(),SharedData.getInstance().getImage(), SharedData.getInstance().getLocation(),
                Session.getInstance().getLoggedInUser().getUserID(),rescueCenterId,"User");
        System.out.println("Clicked Rescue Center ID: " + rescueCenterId);

    }

}
