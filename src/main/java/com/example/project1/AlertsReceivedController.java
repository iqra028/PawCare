package com.example.project1;

import com.example.project1.BLL.PawCare;
import com.example.project1.BLL.SharedData;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.List;

public class AlertsReceivedController extends RescueCenterMenuController {

    @FXML
    private VBox container; // This is the VBox where alert panes will be added

    @FXML
    private Button sendNearbyVolunteersButton;
    @FXML
    private Button dispatchTeamButton;

    private PawCare pawCare; // Declare PawCare object

    @FXML
    public void initialize() {
        super.initialize();
        pawCare = new PawCare(); // Create a new instance of PawCare
        loadAlerts(); // Load the alerts when the page initializes
    }

    // Method to load dynamic alerts
    private void loadAlerts() {
        // Retrieve the list of alerts from SharedData or PawCare
        List<String> alerts= null ;//= SharedData.getInstance().getAlerts(); // Assuming alerts are stored here
        if(alerts!=null) {
            for (String alert : alerts) {
                // Create a new pane for each alert
                Pane alertPane = createAlertPane(alert);

                // Add the alert pane to the container (VBox)
                container.getChildren().add(alertPane);
            }
        }
    }

    // Helper method to create the alert pane dynamically
    private Pane createAlertPane(String alertDetails) {
        // Creating a new Pane to hold the alert details
        Pane alertPane = new Pane();
        alertPane.setPrefHeight(130);
        alertPane.setPrefWidth(686);
        alertPane.setStyle("-fx-background-color: #EEB673;");

        // Label for the injured animal's details (e.g., "Injured Animal Found at [location]")
        Label injuredLabel = new Label("Injured Animal Found at: " + alertDetails);
        injuredLabel.setStyle("-fx-font-size: 24px;");
        injuredLabel.setLayoutX(25);
        injuredLabel.setLayoutY(18);

        // Add the label to the alert pane
        alertPane.getChildren().add(injuredLabel);

        // Button to send nearby volunteers
        Button sendVolunteersButton = new Button("Send Nearby Volunteers");
        sendVolunteersButton.setStyle("-fx-background-color: #D08122;");
        sendVolunteersButton.setLayoutX(517);
        sendVolunteersButton.setLayoutY(87);
        sendVolunteersButton.setOnAction(e -> handleSendVolunteers(alertDetails));

        // Add the button to the alert pane
        alertPane.getChildren().add(sendVolunteersButton);

        // Button to dispatch a team
        Button dispatchButton = new Button("Dispatch Team");
        dispatchButton.setStyle("-fx-background-color: #D08122;");
        dispatchButton.setLayoutX(409);
        dispatchButton.setLayoutY(87);
        dispatchButton.setOnAction(e -> handleDispatchTeam(alertDetails));

        // Add the button to the alert pane
        alertPane.getChildren().add(dispatchButton);

        return alertPane;
    }

    // Handler for sending nearby volunteers (can be more detailed based on the alert info)
    private void handleSendVolunteers(String alertDetails) {
        System.out.println("Sending nearby volunteers for alert: " + alertDetails);
        // Create and save the alert through PawCare
        pawCare.createAlert("AnimalType", "Breed", "InjuryDescription", "imagePath", new double[]{0.0, 0.0});
    }

    // Handler for dispatching a team (can be more detailed based on the alert info)
    private void handleDispatchTeam(String alertDetails) {
        System.out.println("Dispatching team for alert: " + alertDetails);
        // Create and save the alert through PawCare
        pawCare.createAlert("AnimalType", "Breed", "InjuryDescription", "imagePath", new double[]{0.0, 0.0});
    }
}
