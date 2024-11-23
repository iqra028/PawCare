package com.example.project1;
import com.example.project1.BLL.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import java.util.List;

import java.io.IOException;

public class AnimalProfilesControllerAvA  extends RescueCenterMenuController implements RequiresSharedData{

    @FXML
    private Button addAnimalBtn;
    @FXML
    private VBox animalContainer;
    private List<Profile> animalProfiles;
    PawCare pawCare;
    private LoginClassCredentials loginCredentials;
    @FXML
    public void initialize() {

        super.initialize();

    }
    public void start(){
        addAnimalBtn.setOnAction(event -> openAddAnimalPage());
        animalProfiles = getAnimalsFromRescueCenter();
        displayAnimalProfiles();
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

    private void displayAnimalProfiles() {
        animalContainer.getChildren().clear(); // Clear any existing profiles

        if (animalProfiles == null || animalProfiles.isEmpty()) {
            Label noProfilesLabel = new Label("No animal profiles available.");
            noProfilesLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #D08122;");
            animalContainer.getChildren().add(noProfilesLabel);
            return;
        }

        for (Profile animalProf : animalProfiles) {
            Pane animalPane = createAnimalProfile(
                    animalProf.getAnimal().getName(),
                    animalProf.getAnimal().getType(),
                    animalProf.getAnimal().getBreed(),
                    animalProf.getAnimal().getColor(),
                    animalProf.getAnimal().isVisitedVet(),
                    animalProf.getAnimal().isWithVet(),
                    animalProf.getAnimal().getImage()
            );
            animalContainer.getChildren().add(animalPane);
        }
    }

    private Pane createAnimalProfile(String name, String type, String breed, String color, boolean visitedVet, boolean withVet, Image image) {
        // Create a new Pane for the animal profile
        Pane profilePane = new Pane();
        profilePane.setPrefSize(674, 287);
        profilePane.setStyle("-fx-background-color: #ffffff; -fx-border-color: #D08122; -fx-border-width: 1; -fx-border-radius: 10;");

        // Animal Image
        ImageView img = new ImageView();
        img.setFitHeight(500); // Adjust height as needed
        img.setFitWidth(250); // Adjust width as needed
        img.setLayoutX(20);   // Set X position
        img.setLayoutY(40);   // Set Y position
        img.setImage(image);
        img.setPreserveRatio(true);  // Maintain the aspect ratio of the image

        // Labels
        Label nameLabel = new Label(name);
        nameLabel.setLayoutX(300); // Adjust layout X position as needed
        nameLabel.setLayoutY(40);
        nameLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label typeLabel = new Label("Type: " + type);
        typeLabel.setLayoutX(300);
        typeLabel.setLayoutY(70);

        Label breedLabel = new Label("Breed: " + breed);
        breedLabel.setLayoutX(300);
        breedLabel.setLayoutY(100);

        Label colorLabel = new Label("Color: " + color);
        colorLabel.setLayoutX(300);
        colorLabel.setLayoutY(130);

        Label visitedVetLabel = new Label("Visited Vet: " + (visitedVet ? "Yes" : "No"));
        visitedVetLabel.setLayoutX(300);
        visitedVetLabel.setLayoutY(160);

        Label withVetLabel = new Label("With Vet: " + (withVet ? "Yes" : "No"));
        withVetLabel.setLayoutX(300);
        withVetLabel.setLayoutY(190);

        // Buttons
        Button editButton = new Button("Edit");
        editButton.setLayoutX(499);
        editButton.setLayoutY(37);
        editButton.setPrefSize(155, 34);
        editButton.setStyle("-fx-background-color: #D08122;");

        Button deleteButton = new Button("Delete");
        deleteButton.setLayoutX(499);
        deleteButton.setLayoutY(93);
        deleteButton.setPrefSize(155, 34);
        deleteButton.setStyle("-fx-background-color: #D08122;");

        Button adoptionButton = new Button("Put up for Adoption");
        adoptionButton.setLayoutX(499);
        adoptionButton.setLayoutY(149);
        adoptionButton.setPrefSize(155, 34);
        adoptionButton.setStyle("-fx-background-color: #D08122;");

        Button reportButton = new Button("Show Injury Report");
        reportButton.setLayoutX(499);
        reportButton.setLayoutY(204);
        reportButton.setPrefSize(155, 34);
        reportButton.setStyle("-fx-background-color: #D08122;");

        // Add children to the pane
        profilePane.getChildren().addAll(img, nameLabel, typeLabel, breedLabel, colorLabel, visitedVetLabel, withVetLabel, editButton, deleteButton, adoptionButton, reportButton);

        return profilePane;
    }


    private List<Profile> getAnimalsFromRescueCenter() {
        List<Profile> animalProfile;
        RescueCenter rc= Session.getInstance().getLoggedInRescueCenter();
        animalProfile =rc.getAnimalProfiles();
        return animalProfile;

    }
    private void openAddAnimalPage() {
        try {
            HelloApplication.getInstance().changeScene("AddAnimal.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
