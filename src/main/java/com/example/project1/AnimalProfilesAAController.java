package com.example.project1;

import com.example.project1.BLL.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class AnimalProfilesAAController extends RescueCenterMenuController implements RequiresSharedData {
    private PawCare pawCare;
    private LoginClassCredentials loginCredentials;
    private List<Profile> adoptionProfiles;
    @FXML
    private VBox animalContainer;
    @FXML
    private Button addAnimalBtn;
    @FXML
    private Button AvailableAnimals;
    @FXML
    public void initialize() {
        super.initialize();
    }
    public void start(){

        adoptionProfiles = getAnimalsFromRescueCenter();
        addAnimalBtn.setOnAction(event -> openAddAnimalPage());
        AvailableAnimals.setOnAction(event->openAVA());
        displayAdoptionProfiles();
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
    private List<Profile> getAnimalsFromRescueCenter() {
        List<Profile> adoptionProfile;
        RescueCenter rc= pawCare.getRescueCenterbyUsername(loginCredentials.getUsername());
        adoptionProfile =rc.getAdoptionProfiles();
        return adoptionProfile;

    }
    private void displayAdoptionProfiles() {
        animalContainer.getChildren().clear(); // Clear any existing profiles

        if (adoptionProfiles == null || adoptionProfiles.isEmpty()) {
            Label noProfilesLabel = new Label("No animal profiles available.");
            noProfilesLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #D08122;");
            animalContainer.getChildren().add(noProfilesLabel);
            return;
        }

        for (Profile adoptionProf : adoptionProfiles) {
            Pane animalPane = createAdoptionProfile(adoptionProf); // Pass the entire Profile object
            animalContainer.getChildren().add(animalPane);
        }
    }
    private Pane createAdoptionProfile(Profile animalProf) {
        Animal animal = animalProf.getAnimal();

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
        img.setImage(animal.getImage());
        img.setPreserveRatio(true); // Maintain the aspect ratio of the image

        // Labels
        Label nameLabel = new Label(animal.getName());
        nameLabel.setLayoutX(300); // Adjust layout X position as needed
        nameLabel.setLayoutY(40);
        nameLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label typeLabel = new Label("Type: " + animal.getType());
        typeLabel.setLayoutX(300);
        typeLabel.setLayoutY(70);

        Label breedLabel = new Label("Breed: " + animal.getBreed());
        breedLabel.setLayoutX(300);
        breedLabel.setLayoutY(100);

        Label colorLabel = new Label("Color: " + animal.getColor());
        colorLabel.setLayoutX(300);
        colorLabel.setLayoutY(130);

        Label visitedVetLabel = new Label("Visited Vet: " + (animal.isVisitedVet() ? "Yes" : "No"));
        visitedVetLabel.setLayoutX(300);
        visitedVetLabel.setLayoutY(160);

        Label withVetLabel = new Label("With Vet: " + (animal.isWithVet() ? "Yes" : "No"));
        withVetLabel.setLayoutX(300);
        withVetLabel.setLayoutY(190);

        // Buttons
        Button editButton = new Button("Edit");
        editButton.setLayoutX(499);
        editButton.setLayoutY(37);
        editButton.setPrefSize(155, 34);
        editButton.setStyle("-fx-background-color: #D08122;");

        //new stuff :(
        editButton.setOnAction(event -> {
            SharedProfile.getInstance().setSelectedAnimalProfile(animalProf);
            try {
                HelloApplication.getInstance().changeScene("EditAnimal.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        Button deleteButton = new Button("Delete");
        deleteButton.setLayoutX(499);
        deleteButton.setLayoutY(93);
        deleteButton.setPrefSize(155, 34);
        deleteButton.setStyle("-fx-background-color: #D08122;");

        // new stuff:
        deleteButton.setOnAction(event -> {
            boolean deleted = pawCare.deleteAnimalProfile(animalProf);
            if (deleted) { javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Profile deleted successfully!");
                alert.showAndWait();
                pawCare.deleteAnimalProf(animalProf,loginCredentials.getUsername());
            } else {
                javafx.scene.control.Alert alert = new javafx.scene.control.Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Error deleting profile.");
                alert.showAndWait();
            }

        });

        Button reportButton = new Button("Show Injury Report");
        reportButton.setLayoutX(499);
        reportButton.setLayoutY(149);
        reportButton.setPrefSize(155, 34);
        reportButton.setStyle("-fx-background-color: #D08122;");
        reportButton.setOnAction(event -> {
           // injuryReport r=pawCare.retreivereport(animalProf,loginCredentials.getUsername());
            SharedProfile.getInstance().setSelectedAnimalProfile(animalProf);
            try {
                System.out.println("icameheree");
                HelloApplication.getInstance().changeScene("displayInjuryReportforRescueCenter.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        // Add children to the pane
        profilePane.getChildren().addAll(img, nameLabel, typeLabel, breedLabel, colorLabel, visitedVetLabel, withVetLabel, editButton, deleteButton, reportButton);

        return profilePane;
    }
    private void openAddAnimalPage() {
        try {
            HelloApplication.getInstance().changeScene("AddAnimal.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void openAVA()
    {
        try {
            HelloApplication.getInstance().changeScene("AnimalProfileAvailableAnimals.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
