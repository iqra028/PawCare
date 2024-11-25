package com.example.project1;

import com.example.project1.BLL.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.List;

public class AlertsReceivedController extends RescueCenterMenuController implements RequiresSharedData {

    @FXML
    private VBox container;

    private PawCare pawCare;
    private LoginClassCredentials loginCredentials;

    @FXML
    public void initialize() {
        super.initialize();
        System.out.println("AlertsReceivedController initialized.");
    }

    public void setSharedData(PawCare pawCare, LoginClassCredentials loginCredentials) {
        if (pawCare == null || loginCredentials == null) {
            System.err.println("Error: Shared data is null.");
            return;
        }

        this.pawCare = pawCare;
        this.loginCredentials = loginCredentials;
        System.out.println("Shared data set successfully.");
        loadAlertRequests();
    }

    private void loadAlertRequests() {
        if (pawCare == null) {
            System.err.println("Error: PawCare is not initialized.");
            return;
        }

        List<Alert> alerts = pawCare.getAlertsFromDatabase(
                loginCredentials.getUsername()
        );

        container.getChildren().clear();

        for (Alert alert : alerts) {
            Pane alertPane = createAlertPane(alert);
            container.getChildren().add(alertPane);
        }
    }


    private Pane createAlertPane(Alert alert) {
        Pane alertPane = new Pane();
        alertPane.setPrefSize(780, 250);
        alertPane.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #D08122; -fx-border-width: 2; -fx-border-radius: 8; -fx-background-radius: 8;");
        alertPane.setEffect(new DropShadow(10, Color.GRAY));
        alertPane.setUserData(alert); // Attach the alert for easier reference later

        // Image
        ImageView alertImageView = new ImageView();
        alertImageView.setLayoutX(20);
        alertImageView.setLayoutY(20);
        alertImageView.setFitWidth(150);
        alertImageView.setFitHeight(150);
        alertImageView.setStyle("-fx-border-color: #D08122; -fx-border-width: 2;");
        if (alert.getImage() != null) {
            alertImageView.setImage(alert.getImage());
        } else {
            alertImageView.setImage(new Image("default_image_placeholder.png")); // Default placeholder
        }

        // Animal Type
        Label alertTypeLabel = new Label("Animal Type: " + alert.getType());
        alertTypeLabel.setLayoutX(200);
        alertTypeLabel.setLayoutY(20);
        alertTypeLabel.setFont(Font.font("Arial", 16));
        alertTypeLabel.setStyle("-fx-font-weight: bold;");

        // Location
        Label locationLabel = new Label("Location: " + formatLocation(alert.getLocation()));
        locationLabel.setLayoutX(200);
        locationLabel.setLayoutY(50);
        locationLabel.setFont(Font.font("Arial", 14));

        // Breed
        Label breedLabel = new Label("Breed: " + (alert.getBreed() != null ? alert.getBreed() : "Unknown"));
        breedLabel.setLayoutX(200);
        breedLabel.setLayoutY(80);
        breedLabel.setFont(Font.font("Arial", 14));

        // Message
        Label messageLabel = new Label("Message: " + (alert.getMessage() != null ? alert.getMessage() : "None"));
        messageLabel.setLayoutX(200);
        messageLabel.setLayoutY(110);
        messageLabel.setFont(Font.font("Arial", 14));

        // Date
        Label dateLabel = new Label("Date: " + (alert.getDateCreated() != null ? alert.getDateCreated().toString() : "Unknown"));
        dateLabel.setLayoutX(200);
        dateLabel.setLayoutY(140);
        dateLabel.setFont(Font.font("Arial", 14));

        // User
        Label userIdLabel = new Label("User: " + (alert.getUserid() != null ? pawCare.getUserNameByUserid(alert.getUserid()) : "Unknown"));
        userIdLabel.setLayoutX(200);
        userIdLabel.setLayoutY(170);
        userIdLabel.setFont(Font.font("Arial", 14));
        userIdLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #333333;");

        // Buttons
        Button sendVolunteersButton = new Button("Send Nearby Volunteers");
        sendVolunteersButton.setLayoutX(500);
        sendVolunteersButton.setLayoutY(70);
        sendVolunteersButton.setPrefSize(150, 30);
        sendVolunteersButton.setStyle("-fx-background-color: #D08122; -fx-text-fill: white; -fx-font-size: 14; -fx-background-radius: 5;");
        sendVolunteersButton.setOnAction(event -> handleSendVolunteers(alert));

        Button dispatchButton = new Button("Dispatch Team");
        dispatchButton.setLayoutX(500);
        dispatchButton.setLayoutY(120);
        dispatchButton.setPrefSize(150, 30);
        dispatchButton.setStyle("-fx-background-color: #D08122; -fx-text-fill: white; -fx-font-size: 14; -fx-background-radius: 5;");
        dispatchButton.setOnAction(event -> handleDispatchTeam(alert));

        alertPane.getChildren().addAll(
                alertImageView, alertTypeLabel, locationLabel, breedLabel, messageLabel, dateLabel, userIdLabel,
                sendVolunteersButton, dispatchButton
        );

        return alertPane;
    }

    private String formatLocation(double[] location) {
        if (location != null && location.length == 2) {
            return String.format("%.6f, %.6f", location[0], location[1]);
        }
        return "Unavailable";
    }

    private void handleSendVolunteers(Alert alert) {
        try {
            pawCare.sendAlerttoVolunteer(alert);
            System.out.println("Volunteers alerted for alert: " + alert.getaAlertID());
            removeAlertFromView(alert);
            showConfirmationDialog("Volunteers have been alerted for: " + alert.getType());
        } catch (Exception e) {
            System.err.println("Failed to send alert to volunteers: " + e.getMessage());
            showErrorDialog("Failed to send alert to volunteers. Please try again.");
        }
    }

    private void handleDispatchTeam(Alert alert) {
        try {
            pawCare.setAlertToCompleted(alert.getaAlertID());
            alert.setCompleted(true);
            System.out.println("Team dispatched for alert: " + alert.getaAlertID());
            removeAlertFromView(alert);
            showConfirmationDialog("Team has been dispatched for: " + alert.getType());
        } catch (Exception e) {
            System.err.println("Failed to dispatch team: " + e.getMessage());
            showErrorDialog("Failed to dispatch team. Please try again.");
        }
    }

    private void removeAlertFromView(Alert alert) {
        container.getChildren().removeIf(node -> node instanceof Pane && alert.equals(node.getUserData()));
    }

    private void showConfirmationDialog(String message) {
        javafx.scene.control.Alert confirmationAlert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText(message);
        confirmationAlert.showAndWait();
    }

    private void showErrorDialog(String message) {
        javafx.scene.control.Alert errorAlert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        errorAlert.setTitle("Error");
        errorAlert.setHeaderText(null);
        errorAlert.setContentText(message);
        errorAlert.showAndWait();
    }
}
