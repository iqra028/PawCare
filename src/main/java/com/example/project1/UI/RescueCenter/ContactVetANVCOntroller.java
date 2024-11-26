package com.example.project1.UI.RescueCenter;

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
import java.util.ArrayList;
import java.util.List;

public class ContactVetANVCOntroller extends RescueCenterMenuController implements RequiresSharedData {


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

        animalProfiles = getAnimalProfilestoVisitVet();
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
            Pane animalPane = createAnimalProfile(animalProf); // Pass the entire Profile object
            animalContainer.getChildren().add(animalPane);
        }
    }

    private Pane createAnimalProfile(Profile animalProf) {
        Animal animal = animalProf.getAnimal(); // Access the Animal object from the Profile

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
        Button editButton = new Button("ContactVet");
        editButton.setLayoutX(499);
        editButton.setLayoutY(37);
        editButton.setPrefSize(155, 34);
        editButton.setStyle("-fx-background-color: #D08122;");

        //new stuff :(
        editButton.setOnAction(event -> {
            SharedProfile.getInstance().setSelectedAnimalProfile(animalProf);
            try {
                HelloApplication.getInstance().changeScene("VisitVet.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });


        // Add children to the pane
        profilePane.getChildren().addAll(img, nameLabel, typeLabel, breedLabel, colorLabel, visitedVetLabel, withVetLabel, editButton);

        return profilePane;
    }


    private List<Profile> getAnimalsFromRescueCenter() {
        List<Profile> animalProfile;

        RescueCenter rc= pawCare.getRescueCenterbyUsername(loginCredentials.getUsername());
        animalProfile =rc.getAnimalProfiles();

        return animalProfile;

    }
    private List<Profile> getAnimalProfilestoVisitVet(){
       List<Profile> profile= getAnimalsFromRescueCenter();
       List<Profile> p =new ArrayList<>();
       for(Profile animalProfile : profile){
           if(!animalProfile.getAnimal().isVisitedVet()&&!animalProfile.getAnimal().isWithVet() )
           {
               p.add(animalProfile);

           }
       }
       return p;
    }

    public void  openAdoptionProfiles()
    {
        try {
            HelloApplication.getInstance().changeScene("AnimalProfileAdoptionAnimals.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void openAddAnimalPage() {
        try {
            HelloApplication.getInstance().changeScene("AddAnimal.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
