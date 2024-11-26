package com.example.project1.UI.User;

import com.example.project1.BLL.*;
import com.example.project1.BLL.Profiles.Profile;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class ReportMissingAnimalController extends UserMenuController implements RequiresSharedData {

    @FXML
    private Button submitButton;
    @FXML
    private TextField animalTypeField;
    @FXML
    private TextField breedField;
    @FXML
    private TextField colorField;
    @FXML
    private TextField nameField;
    private LoginClassCredentials loginCredentials;
    private PawCare pawCare;
    @FXML
    private VBox profilesPane;

    @FXML
    public void initialize() {
        super.initialize();
    }

    void start() {
        submitButton.setOnAction(event -> handleSubmit());
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

    @FXML
    void handleSubmit() {
        String animalType = animalTypeField.getText();
        String name = nameField.getText();
        String breed = breedField.getText();
        String color = colorField.getText();

        profilesPane.getChildren().clear();

        Label potentialMatchesLabel = new Label("           Potential Matches");
        potentialMatchesLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-alignment: center; -fx-padding: 10px;");
        potentialMatchesLabel.setPrefWidth(674);  // Set the width to match the profile pane width
        potentialMatchesLabel.setLayoutX(0); // Center horizontally

        profilesPane.getChildren().add(potentialMatchesLabel);

        ArrayList<RescueCenter> rc = pawCare.getRescueCentersProfiles(animalType, name, breed, color);

        for (RescueCenter rescueCenter : rc) {
            Label rescueCenterLabel = new Label("Rescue Center: " + rescueCenter.getName());
            rescueCenterLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

            Label emailLabel = new Label("Email: " + rescueCenter.getEmail());
            emailLabel.setStyle("-fx-font-size: 14px;");

            Label phoneLabel = new Label("Phone: " + rescueCenter.getPhoneNumber());
            phoneLabel.setStyle("-fx-font-size: 14px;");

            VBox rescueCenterInfo = new VBox(rescueCenterLabel, emailLabel, phoneLabel);
            rescueCenterInfo.setSpacing(5);

            ScrollPane profileScrollPane = new ScrollPane();
            profileScrollPane.setContent(rescueCenterInfo);
            profileScrollPane.setStyle("-fx-background-color: #f4f4f4; -fx-border-radius: 10;");

            profilesPane.getChildren().add(profileScrollPane);

            addProfilesToVBox(rescueCenter.getAnimalProfiles(), rescueCenter);
            addProfilesToVBox(rescueCenter.getAdoptionProfiles(), rescueCenter);
        }

        profilesPane.setVisible(true);
    }


    private void addProfilesToVBox(ArrayList<Profile> profiles, RescueCenter rescueCenter) {
        for (Profile pf : profiles) {
            Animal animal = pf.getAnimal();

            Pane profilePane = new Pane();
            profilePane.setPrefSize(674, 287);
            profilePane.setStyle("-fx-background-color: #ffffff; -fx-border-color: #D08122; -fx-border-width: 1; -fx-border-radius: 10;");

            ImageView img = new ImageView();
            img.setFitHeight(500); // Adjust height as needed
            img.setFitWidth(250); // Adjust width as needed
            img.setLayoutX(20);   // Set X position
            img.setLayoutY(40);   // Set Y position
            img.setImage(animal.getImage());
            img.setPreserveRatio(true); // Maintain the aspect ratio of the image

            // Labels
            Label nameLabel = new Label(animal.getName());
            nameLabel.setLayoutX(300);
            nameLabel.setLayoutY(40);
            nameLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

            Label typeLabel = new Label("Type: " + animal.getType());
            typeLabel.setLayoutX(300);
            typeLabel.setLayoutY(70);
            typeLabel.setStyle("-fx-font-size: 16px;");

            Label breedLabel = new Label("Breed: " + animal.getBreed());
            breedLabel.setLayoutX(300);
            breedLabel.setLayoutY(100);
            breedLabel.setStyle("-fx-font-size: 16px;");

            Label colorLabel = new Label("Color: " + animal.getColor());
            colorLabel.setLayoutX(300);
            colorLabel.setLayoutY(130);
            colorLabel.setStyle("-fx-font-size: 16px;");

            profilePane.getChildren().addAll(img, nameLabel, typeLabel, breedLabel, colorLabel);

            profilesPane.getChildren().add(profilePane);
        }
    }

}
