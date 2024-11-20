package com.example.project1;

import com.example.project1.BLL.Alert;
import com.example.project1.BLL.PawCare;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.util.List;

public class AdoptionRequestsController extends RescueCenterMenuController {

    @FXML
    private VBox container; // Reference to the VBox in FXML where alerts will be displayed
    PawCare pawCare;
    @FXML
    public void initialize() {
        super.initialize();
        loadAdoptionRequests();
    }

    // This function is responsible for loading the adoption requests (alerts) from the database
    private void loadAdoptionRequests() {
        pawCare = new PawCare();
        List<Alert> alerts = pawCare.getAlertsFromDatabase(); // Fetch alerts from the database

        // Clear existing alerts if any
        container.getChildren().clear();

        // Dynamically create and add UI elements for each alert
        for (Alert alert : alerts) {
            // Create a new Pane to hold the information for each alert
            Pane alertPane = createAlertPane(alert);
            container.getChildren().add(alertPane); // Add the pane to the VBox
        }
    }

    // Fetch alerts from the database (use your existing database handler)
    // Create a Pane dynamically to display alert information
    private Pane createAlertPane(Alert alert) {
        Pane alertPane = new Pane();
        alertPane.setPrefSize(686.0, 130.0);
        alertPane.setStyle("-fx-background-color: #EEB673;");
        alertPane.setEffect(new DropShadow()); // Add shadow effect for a nice visual effect

        // Label to display type or other info
        Label alertTypeLabel = new Label("Alert Type: " + alert.getType());
        alertTypeLabel.setLayoutX(25.0);
        alertTypeLabel.setLayoutY(18.0);
        alertTypeLabel.setFont(new Font(18));

        // Location label
        Label locationLabel = new Label("Location: " + alert.getLocation());
        locationLabel.setLayoutX(25.0);
        locationLabel.setLayoutY(57.0);
        locationLabel.setFont(new Font(18));

        // Button to send nearby volunteers
        Button sendVolunteersButton = new Button("Send Nearby Volunteers");
        sendVolunteersButton.setLayoutX(517.0);
        sendVolunteersButton.setLayoutY(87.0);
        sendVolunteersButton.setStyle("-fx-background-color: #D08122;");
        sendVolunteersButton.setOnAction(event -> handleSendVolunteers(alert)); // Button click action

        // Button to dispatch team
        Button dispatchButton = new Button("Dispatch Team");
        dispatchButton.setLayoutX(409.0);
        dispatchButton.setLayoutY(87.0);
        dispatchButton.setStyle("-fx-background-color: #D08122;");
        dispatchButton.setOnAction(event -> handleDispatchTeam(alert)); // Button click action

        // Add the labels and buttons to the alert pane
        alertPane.getChildren().addAll(alertTypeLabel, locationLabel, sendVolunteersButton, dispatchButton);

        return alertPane;
    }

    // Handle the event when the "Send Nearby Volunteers" button is clicked
    private void handleSendVolunteers(Alert alert) {
        // Your logic to send volunteers, maybe display more info or call a method in your DB handler
        System.out.println("Sending nearby volunteers for alert: ");
    }

    // Handle the event when the "Dispatch Team" button is clicked
    private void handleDispatchTeam(Alert alert) {
        // Your logic to dispatch the team, maybe display more info or call a method in your DB handler
        System.out.println("Dispatching team for alert: ");
    }
}
