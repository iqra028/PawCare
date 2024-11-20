package com.example.project1;
import com.example.project1.BLL.Donation;
import com.example.project1.BLL.PawCare;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class DonationsMadeController extends RescueCenterMenuController {

    private PawCare pawCare;

    @FXML
    private VBox container;

    @FXML
    public void initialize() {
        super.initialize();
        pawCare = new PawCare();
        List<Donation> donationList = pawCare.DisplayDonationRecords();

        // Display each donation dynamically
        displayDonations(donationList);
    }

    private void displayDonations(List<Donation> donationList) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (Donation donation : donationList) {
            Pane pane = new Pane();
            pane.setPrefSize(650.0, 100.0); // Adjusted size for better alignment
            pane.setStyle("-fx-background-color: #f9f9f9; -fx-border-color: #cccccc; -fx-border-width: 1; -fx-border-radius: 10; -fx-padding: 10; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.1), 10, 0.1, 0, 3);");

            // Donor Label
            Label donorLabel = new Label("Donor: " + pawCare.getUserNameByUserid(donation.getUserid()));
            donorLabel.setLayoutX(20.0);
            donorLabel.setLayoutY(20.0);
            donorLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

            // Date Label
            Label dateLabel = new Label("Date: " + donation.getDateCreated().format(dateFormatter));
            dateLabel.setLayoutX(20.0);
            dateLabel.setLayoutY(50.0);
            dateLabel.setStyle("-fx-font-size: 14px;");

            // Amount Label
            Label amountLabel = new Label("$" + donation.getAmount());
            amountLabel.setLayoutX(500.0); // Right-align amount
            amountLabel.setLayoutY(35.0);
            amountLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #4caf50;");

            // Add the labels to the pane
            pane.getChildren().addAll(donorLabel, dateLabel, amountLabel);

            // Add the pane to the VBox container
            container.getChildren().add(pane);
        }
    }

}
