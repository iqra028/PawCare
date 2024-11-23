package com.example.project1;

import com.example.project1.BLL.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.effect.DropShadow;
import javafx.scene.text.Font;

import java.util.List;

public class AlertsReceivedController extends RescueCenterMenuController implements RequiresSharedData {

    @FXML
    private VBox container; // Reference to the VBox in FXML where alerts will be displayed

    @FXML
    private Button sendNearbyVolunteers; // Reference to the Send Nearby Volunteers button
    @FXML
    private Button dispatchTeamButton; // Reference to the Dispatch Team button

    PawCare pawCare;
    private LoginClassCredentials loginCredentials;

    @FXML
    public void initialize() {
        super.initialize();

    }

    public void start(){
        loadAllertsRequests();
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
    // This function is responsible for loading the adoption requests (alerts) from the database
    private void loadAllertsRequests() {
        List<Alert> alerts = pawCare.getAlertsFromDatabase(Session.getInstance().getLoggedInRescueCenter().getUserName()); // Fetch alerts from the database

        // Clear existing alerts if any
        container.getChildren().clear();

        // Dynamically create and add UI elements for each alert
        for (Alert alert : alerts) {
            // Create a new Pane to hold the information for each alert
            Pane alertPane = createAlertPane(alert);
            container.getChildren().add(alertPane); // Add the pane to the VBox
        }
    }

    private Pane createAlertPane(Alert alert) {
        Pane alertPane = new Pane();
        alertPane.setPrefSize(700.0, 200.0); // Slightly wider and taller for a cleaner layout
        alertPane.setStyle("-fx-background-color: #F5F5DC; -fx-border-color: #D08122; -fx-border-width: 2; -fx-border-radius: 8; -fx-background-radius: 8;");
        alertPane.setEffect(new DropShadow(10, javafx.scene.paint.Color.GRAY)); // Softer shadow effect

        // Animal Type
        Label alertTypeLabel = new Label("Animal Type: " + alert.getType());
        alertTypeLabel.setLayoutX(20.0);
        alertTypeLabel.setLayoutY(15.0);
        alertTypeLabel.setFont(Font.font("Arial", 16));
        alertTypeLabel.setStyle("-fx-font-weight: bold;");

        // Location
        double[] location = alert.getLocation();
        String locationText = "Location: ";
        if (location != null && location.length == 2) {
            locationText += String.format("%.6f, %.6f", location[0], location[1]); // Formats to 6 decimal places
        } else {
            locationText += "Unavailable";
        }
        Label locationLabel = new Label(locationText);
        locationLabel.setLayoutX(20.0);
        locationLabel.setLayoutY(45.0);
        locationLabel.setFont(Font.font("Arial", 14));

        // Breed
        Label breedLabel = new Label("Breed: " + (alert.getBreed() != null ? alert.getBreed() : "Unknown"));
        breedLabel.setLayoutX(20.0);
        breedLabel.setLayoutY(75.0);
        breedLabel.setFont(Font.font("Arial", 14));

        // Message
        Label messageLabel = new Label("Message: " + (alert.getMessage() != null ? alert.getMessage() : "None"));
        messageLabel.setLayoutX(20.0);
        messageLabel.setLayoutY(105.0);
        messageLabel.setFont(Font.font("Arial", 14));

        // Date
        Label dateLabel = new Label("Date: " + (alert.getDateCreated() != null ? alert.getDateCreated().toString() : "Unknown"));
        dateLabel.setLayoutX(20.0);
        dateLabel.setLayoutY(135.0);
        dateLabel.setFont(Font.font("Arial", 14));

        // User ID
        Label userIdLabel = new Label("User ID: " + (alert.getUserid() != null ? alert.getUserid() : "Unknown"));
        userIdLabel.setLayoutX(380.0);
        userIdLabel.setLayoutY(15.0);
        userIdLabel.setFont(Font.font("Arial", 14));
        userIdLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #333333;");

        // Buttons
        Button sendVolunteersButton = new Button("Send Nearby Volunteers");
        sendVolunteersButton.setLayoutX(380.0);
        sendVolunteersButton.setLayoutY(120.0);
        sendVolunteersButton.setPrefSize(150.0, 30.0);
        sendVolunteersButton.setStyle("-fx-background-color: #D08122; -fx-text-fill: white; -fx-font-size: 14; -fx-background-radius: 5;");
        sendVolunteersButton.setOnAction(event -> handleSendVolunteers(alert)); // Pass the alert to the handler

        Button dispatchButton = new Button("Dispatch Team");
        dispatchButton.setLayoutX(550.0);
        dispatchButton.setLayoutY(120.0);
        dispatchButton.setPrefSize(120.0, 30.0);
        dispatchButton.setStyle("-fx-background-color: #D08122; -fx-text-fill: white; -fx-font-size: 14; -fx-background-radius: 5;");
        dispatchButton.setOnAction(event -> handleDispatchTeam(alert)); // Pass the alert to the handler

        // Add all elements to the alert pane
        alertPane.getChildren().addAll(
                alertTypeLabel,
                locationLabel,
                breedLabel,
                messageLabel,
                dateLabel,
                userIdLabel,
                sendVolunteersButton,
                dispatchButton
        );

        return alertPane;
    }



    // Handle the event when the "Send Nearby Volunteers" button is clicked
    private void handleSendVolunteers(Alert alert) {
        try {
            pawCare.sendAlerttoVolunteer(alert); // Call the method from the PawCare class
            System.out.println("Alert sent to nearby volunteers for alert: " + alert.getType());
            // Optionally, display a confirmation dialog to the user
            showConfirmationDialog("Volunteers have been alerted for: " + alert.getType());
        } catch (Exception e) {
            // Handle any exceptions and provide feedback to the user
            System.err.println("Failed to send alert to volunteers: " + e.getMessage());
           // showErrorDialog("Failed to alert volunteers. Please try again.");
        }
    }

    // Handle the event when the "Dispatch Team" button is clicked
    private void handleDispatchTeam(Alert alert) {
        try {
            System.out.println("Dispatching team for alert: " + alert.getType());
            // You can add additional logic here for dispatching the team, like showing more info
        } catch (Exception e) {
            System.err.println("Failed to dispatch team: " + e.getMessage());
            showErrorDialog("Failed to dispatch team. Please try again.");
        }
    }

    // Utility method to show a confirmation dialog
    private void showConfirmationDialog(String message) {
        // Create a simple confirmation dialog (or use an existing UI framework dialog box)
        javafx.scene.control.Alert confirmationAlert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText(message);
        confirmationAlert.showAndWait();
    }

    // Utility method to show an error dialog
    private void showErrorDialog(String message) {
        javafx.scene.control.Alert errorAlert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        errorAlert.setTitle("Error");
        errorAlert.setHeaderText(null);
        errorAlert.setContentText(message);
        errorAlert.showAndWait();
    }
}
