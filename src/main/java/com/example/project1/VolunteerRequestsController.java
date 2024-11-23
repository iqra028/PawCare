package com.example.project1;

import com.example.project1.BLL.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VolunteerRequestsController extends UserMenuController implements RequiresSharedData {



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
    private void loadAllertsRequests() {
        String rcu=loginCredentials.getUsername();

        List<Alert> alerts = pawCare.getRescueCenterAlerts( ); // Fetch alerts from the database
        if(alerts!=null) {
            // Clear existing alerts if any
            container.getChildren().clear();

            // Dynamically create and add UI elements for each alert
            for (Alert alert : alerts) {
                // Create a new Pane to hold the information for each alert
                Pane alertPane = createAlertPane(alert);
                container.getChildren().add(alertPane); // Add the pane to the VBox
            }
        }
        else{
            System.out.println("Error: No alert found.");
        }
    }

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
        double[] location = alert.getLocation();
        String locationText = "Location: ";
        if (location != null && location.length == 2) {
            locationText += String.format("%.6f, %.6f", location[0], location[1]); // Formats to 6 decimal places
        } else {
            locationText += "Unavailable"; // Fallback if location is null or not properly formatted
        }

// Create the label with the formatted location
        Label locationLabel = new Label(locationText);
        locationLabel.setLayoutX(25.0);
        locationLabel.setLayoutY(57.0);
        locationLabel.setFont(new Font(18));

        // Button to dispatch team
        Button completed = new Button("COMPLETED");
        completed.setLayoutX(409.0);
        completed.setLayoutY(87.0);
        completed.setStyle("-fx-background-color: #D08122;");
        completed.setOnAction(event -> setCompleted(alert)); // Pass the alert to the handler

        // Add the labels and buttons to the alert pane
        alertPane.getChildren().addAll(alertTypeLabel, locationLabel, completed);
        return alertPane;
    }



    // Handle the event when the "Dispatch Team" button is clicked
    private void setCompleted(Alert alert) {
        try {
            pawCare.setAlertToCompleted(alert.getaAlertID());
        } catch (Exception e) {;
            showErrorDialog("Failed to complete. Please try again.");
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
