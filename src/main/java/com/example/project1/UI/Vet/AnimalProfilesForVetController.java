package com.example.project1.UI.Vet;

import com.example.project1.BLL.*;
import com.example.project1.BLL.Profiles.Profile;
import com.example.project1.HelloApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class AnimalProfilesForVetController extends VetMenu implements RequiresSharedData {

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
        List<Profile> v=pawCare.loadvetsrequests(pawCare.getVetFromUsername(loginCredentials.getUsername()));
        System.out.println("All available");
        for(Profile p:v)
        {
            System.out.println(p.getAnimal().getName());
        }
        return vet.getCurrentlybeingchecked();
    }

    // Method to display the list of animals for the vet
    public void displayAnimals() {
        List<Profile> animals = getAnimalsForVet();

        // Clear any existing UI components in the pane
        pane1.getChildren().clear();

        // Use a VBox to arrange the profile panes vertically
        VBox profilesContainer = new VBox(10); // 10 is the spacing between items
        profilesContainer.setPrefWidth(700); // Adjust as needed
        profilesContainer.setStyle("-fx-padding: 10;"); // Add padding if needed

        for (Profile animal : animals) {
            Animal a = animal.getAnimal();
            Pane profilePane = new Pane();
            profilePane.setPrefSize(674, 287);
            profilePane.setStyle("-fx-background-color: #ffffff; -fx-border-color: #D08122; -fx-border-width: 1; -fx-border-radius: 10;");

            // Animal Image
            ImageView img = new ImageView();
            img.setFitHeight(150); // Adjust height as needed
            img.setFitWidth(150);  // Adjust width as needed
            img.setLayoutX(20);    // Set X position
            img.setLayoutY(40);    // Set Y position
            img.setImage(a.getImage());
            img.setPreserveRatio(true); // Maintain the aspect ratio of the image

            // Labels
            Label nameLabel = new Label(a.getName());
            nameLabel.setLayoutX(200); // Adjust layout X position as needed
            nameLabel.setLayoutY(40);
            nameLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

            Label typeLabel = new Label("Type: " + a.getType());
            typeLabel.setLayoutX(200);
            typeLabel.setLayoutY(70);

            Label breedLabel = new Label("Breed: " + a.getBreed());
            breedLabel.setLayoutX(200);
            breedLabel.setLayoutY(100);

            Label colorLabel = new Label("Color: " + a.getColor());
            colorLabel.setLayoutX(200);
            colorLabel.setLayoutY(130);

            Label visitedVetLabel = new Label("Visited Vet: " + (a.isVisitedVet() ? "Yes" : "No"));
            visitedVetLabel.setLayoutX(200);
            visitedVetLabel.setLayoutY(160);

            Label withVetLabel = new Label("With Vet: " + (a.isWithVet() ? "Yes" : "No"));
            withVetLabel.setLayoutX(200);
            withVetLabel.setLayoutY(190);

            // Button to display health
            Button displayHealthButton = new Button("Generate Report");
            displayHealthButton.setLayoutX(500); // Adjust X position as needed
            displayHealthButton.setLayoutY(40);  // Adjust Y position as needed
            displayHealthButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 12px; -fx-padding: 5 10;");

            // Action for the "Display Health" button
            if(animal!=null) {
                displayHealthButton.setOnAction(event -> {
                    try {
                        SharedProfile.getInstance().setSelectedAnimalProfile(animal);
                        System.out.println("Selected Animal Profile: " + animal);
                        System.out.println(animal.getRescueCenterId());
                        HelloApplication.getInstance().changeScene("GenerateInjurtReport.fxml");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
            else{
                System.out.println("Selected Animal Profile is null");
            }

            // Add the image, labels, and button to the profilePane
            profilePane.getChildren().addAll(img, nameLabel, typeLabel, breedLabel, colorLabel, visitedVetLabel, withVetLabel, displayHealthButton);

            // Add the profilePane to the main pane (pane1)
            profilesContainer.getChildren().add(profilePane);
        }
        pane1.getChildren().add(profilesContainer);
    }



    // Initialize method from parent class, if any setup is needed
    @FXML
    public void initialize() {
        super.initialize();
    }
}
