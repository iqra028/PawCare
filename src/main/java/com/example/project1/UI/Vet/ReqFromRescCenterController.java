package com.example.project1.UI.Vet;

import com.example.project1.BLL.*;
import com.example.project1.HelloApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.List;

public class ReqFromRescCenterController extends VetMenu implements RequiresSharedData {

    @FXML
    private Pane pane1;

    private PawCare pawCare;
    private LoginClassCredentials loginCredentials;

    // Method to set shared data (PawCare and LoginCredentials)
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

    // Start the display process
    public void start() {
        displayAnimals();
    }

    // Method to fetch the animals associated with the vet (replace with actual logic)
    private List<Profile> getAnimalsForVet() {
        Vets vet = pawCare.getVetfromUsername(loginCredentials.getUsername());
        return vet.getCurrentlybeingchecked();
    }

    // Method to display the list of animals for the vet
    public void displayAnimals() {
        List<Profile> animals = getAnimalsForVet();

        // Clear any existing UI components in the pane
        pane1.getChildren().clear();

        // Iterate through animals to display their details
        for (int i = 0; i < animals.size(); i++) {
            Profile animalProfile = animals.get(i);
            Animal animal = animalProfile.getAnimal();

            // Create a new Pane for the animal profile
            Pane profilePane = new Pane();
            profilePane.setPrefSize(674, 287);
            profilePane.setStyle("-fx-background-color: #ffffff; -fx-border-color: #D08122; -fx-border-width: 1; -fx-border-radius: 10;");

            // Animal Image
            ImageView img = new ImageView();
            img.setFitHeight(150); // Adjust height as needed
            img.setFitWidth(150);  // Adjust width as needed
            img.setLayoutX(20);    // Set X position
            img.setLayoutY(40);    // Set Y position
            img.setImage(animal.getImage());
            img.setPreserveRatio(true); // Maintain the aspect ratio of the image

            // Labels
            Label nameLabel = new Label(animal.getName());
            nameLabel.setLayoutX(200); // Adjust layout X position as needed
            nameLabel.setLayoutY(40);
            nameLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

            Label typeLabel = new Label("Type: " + animal.getType());
            typeLabel.setLayoutX(200);
            typeLabel.setLayoutY(70);

            Label breedLabel = new Label("Breed: " + animal.getBreed());
            breedLabel.setLayoutX(200);
            breedLabel.setLayoutY(100);

            Label colorLabel = new Label("Color: " + animal.getColor());
            colorLabel.setLayoutX(200);
            colorLabel.setLayoutY(130);

            Label visitedVetLabel = new Label("Visited Vet: " + (animal.isVisitedVet() ? "Yes" : "No"));
            visitedVetLabel.setLayoutX(200);
            visitedVetLabel.setLayoutY(160);

            Label withVetLabel = new Label("With Vet: " + (animal.isWithVet() ? "Yes" : "No"));
            withVetLabel.setLayoutX(200);
            withVetLabel.setLayoutY(190);

            // Button to display health
            Button displayHealthButton = new Button("Display Health");
            displayHealthButton.setLayoutX(500); // Adjust X position as needed
            displayHealthButton.setLayoutY(40);  // Adjust Y position as needed
            displayHealthButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 12px; -fx-padding: 5 10;");

            // Action for the "Display Health" button
            displayHealthButton.setOnAction(event -> {
                displayHealthInfo(animalProfile);
            });

            // Add the image, labels, and button to the profilePane
            profilePane.getChildren().addAll(img, nameLabel, typeLabel, breedLabel, colorLabel, visitedVetLabel, withVetLabel, displayHealthButton);

            // Add the profilePane to the main pane (pane1)
            pane1.getChildren().add(profilePane);
        }
    }

    // Method to display the health information of the animal
    private void displayHealthInfo(Profile animalprofile) {
        SharedProfile.getInstance().setSelectedAnimalProfile(animalprofile);
        System.out.println("im correct");
        try {
            HelloApplication.getInstance().changeScene("DisplayHealth.fxml");
        } catch (IOException e) {
            System.out.println("i was the error");
            throw new RuntimeException(e);
        }

    }

    // Initialize method from parent class, if any setup is needed
    @FXML
    public void initialize() {
        super.initialize();
    }
}
