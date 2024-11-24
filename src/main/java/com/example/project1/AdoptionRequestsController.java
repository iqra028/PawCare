package com.example.project1;

import com.example.project1.BLL.AdoptionRequest;
import com.example.project1.BLL.LoginClassCredentials;
import com.example.project1.BLL.PawCare;
import com.example.project1.BLL.RequiresSharedData;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class AdoptionRequestsController extends RescueCenterMenuController implements RequiresSharedData {

    private PawCare pawCare;
    private LoginClassCredentials loginCredentials;

    @FXML
    private VBox container; // The container for dynamically displaying requests

    public void initialize() {
        super.initialize();
    }

    void start() {
        String rescueCenterUsername = loginCredentials.getUsername();
        ArrayList<AdoptionRequest> adoptionRequests = pawCare.getAdoptionRequestsForRescueCenter(rescueCenterUsername);

        // Filter out resolved adoption requests
        ArrayList<AdoptionRequest> unresolvedRequests = new ArrayList<>();
        for (AdoptionRequest request : adoptionRequests) {
            if (!request.getIsResolved()) {
                unresolvedRequests.add(request);
            }
        }

        populateAdoptionRequests(unresolvedRequests);
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

    private void populateAdoptionRequests(ArrayList<AdoptionRequest> adoptionRequests) {
        container.getChildren().clear(); // Clear the container before populating
        for (AdoptionRequest request : adoptionRequests) {
            Pane requestPane = createRequestPane(request);
            container.getChildren().add(requestPane);
        }
    }

    private Pane createRequestPane(AdoptionRequest request) {
        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: #EEB673;");
        pane.setPrefSize(686, 161);

        Label animalIdLabel = new Label("Adoption Request for: " + request.getAnimalId());
        animalIdLabel.setLayoutX(25);
        animalIdLabel.setLayoutY(18);
        animalIdLabel.setStyle("-fx-font-size: 24px;");

        Label adopterLabel = new Label("By: " + request.getUserId());
        adopterLabel.setLayoutX(25);
        adopterLabel.setLayoutY(55);
        adopterLabel.setStyle("-fx-font-size: 18px;");

        Label allergyLabel = new Label("Allergic person in home: " + (request.isHas_allergy() ? "Yes" : "No"));
        allergyLabel.setLayoutX(25);
        allergyLabel.setLayoutY(87);

        Label livingConditionLabel = new Label("Suitable living condition: " + (request.isSuitable_living_conditions() ? "Yes" : "No"));
        livingConditionLabel.setLayoutX(25);
        livingConditionLabel.setLayoutY(104);

        Label reasonLabel = new Label("Reason to adopt: " + request.getReason_to_adopt());
        reasonLabel.setLayoutX(25);
        reasonLabel.setLayoutY(121);

        Button approveButton = new Button("Approve");
        approveButton.setLayoutX(496);
        approveButton.setLayoutY(87);
        approveButton.setPrefSize(83, 34);
        approveButton.setStyle("-fx-background-color: #D08122;");
        approveButton.setOnAction(event -> approveRequest(request));

        Button denyButton = new Button("Deny");
        denyButton.setLayoutX(593);
        denyButton.setLayoutY(87);
        denyButton.setPrefSize(79, 34);
        denyButton.setStyle("-fx-background-color: #D08122;");
        denyButton.setOnAction(event -> denyRequest(request));

        pane.getChildren().addAll(animalIdLabel, adopterLabel, allergyLabel, livingConditionLabel, reasonLabel, approveButton, denyButton);
        return pane;
    }

    private void approveRequest(AdoptionRequest request) {
        request.setApplicationStatus(true);
        request.setIs_resolved(true);
        boolean success = pawCare.handleAdoptionRequest(loginCredentials.getUsername(), request);
        if (success) {
            System.out.println("Request accepted successfully.");
        } else {
            System.out.println("Failed to accept the request.");
        }
    }

    private void denyRequest(AdoptionRequest request) {
        request.setApplicationStatus(false);
        request.setIs_resolved(true);
        boolean success = pawCare.handleAdoptionRequest(loginCredentials.getUsername(), request);
        if (success) {
            System.out.println("Request denied successfully.");
        } else {
            System.out.println("Failed to deny the request.");
        }

    }


}
