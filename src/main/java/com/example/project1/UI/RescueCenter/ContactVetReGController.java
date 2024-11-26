package com.example.project1.UI.RescueCenter;

import com.example.project1.BLL.*;
import com.example.project1.BLL.Location.SharedData;
import com.example.project1.HelloApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class ContactVetReGController extends ContactVet implements RequiresSharedData {
    private PawCare pawCare;
    private LoginClassCredentials loginCredentials;
    @FXML
    private Button RegisteredVets;
    @FXML
    private Button UnvisitedAnimals;

    @FXML
    private VBox vetContainer;

    @FXML
    public void initialize() {
        super.initialize();
        vetContainer.getChildren().clear(); // Clear any existing content
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

    public void start() {
        List<Vets> vets = pawCare.getVets();
        displayVets(vets);
        RegisteredVets.setOnAction(event -> {
            try {
                HelloApplication.getInstance().changeScene("ContactVetRegisteredVet.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        UnvisitedAnimals.setOnAction(event -> {
            try {
                HelloApplication.getInstance().changeScene("ContactVetAnimalsNotVisitedVet.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void displayVets(List<Vets> vets) {
        if (vets == null || vets.isEmpty()) {
            System.out.println("No vets available.");
            return;
        }

        vetContainer.getChildren().clear(); // Clear existing content

        for (Vets vet : vets) {
            Pane vetCard = createVetCard(vet);
            vetContainer.getChildren().add(vetCard);
        }
    }

    private Pane createVetCard(Vets vet) {
        Pane vetCard = new Pane();
        vetCard.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #ccc; -fx-border-radius: 5; -fx-padding: 10;");
        vetCard.setPrefSize(600, 150);

        Label nameLabel = new Label("Name: " + vet.getName());
        nameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        nameLabel.setLayoutX(20);
        nameLabel.setLayoutY(20);

        Label locationLabel = new Label("Location: " + vet.getLocation());
        locationLabel.setLayoutX(20);
        locationLabel.setLayoutY(50);

        Label phoneLabel = new Label("Phone: " + vet.getPhoneNumber());
        phoneLabel.setLayoutX(20);
        phoneLabel.setLayoutY(80);

        Button profileButton = new Button("Check Profile");
        profileButton.setStyle("-fx-background-color: #D08122; -fx-text-fill: white;");
        profileButton.setLayoutX(20);
        profileButton.setLayoutY(110);
        profileButton.setOnAction(event -> {
            // Handle profile button click
            try {
                SharedData.getInstance().setSelectedVet(vet);
                HelloApplication.getInstance().changeScene("VetProfile.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Viewing profile for: " + vet.getName());
        });

        vetCard.getChildren().addAll(nameLabel, locationLabel, phoneLabel, profileButton);
        return vetCard;
    }
}
