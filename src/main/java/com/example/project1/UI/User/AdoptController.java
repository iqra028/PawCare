package com.example.project1.UI.User;

import com.example.project1.BLL.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class AdoptController extends UserMenuController implements RequiresSharedData {

    @FXML
    private com.example.project1.BLL.PawCare pawCare;
    private LoginClassCredentials loginCredentials;
    private ArrayList<Profile> adoptionProfiles;
    @FXML
    private VBox profileContainer;
    @FXML
    private AnchorPane mainContainer;


    public void initialize() {
        super.initialize();
    }

    void start(){
        adoptionProfiles = getAllAdoptionProfiles();
        displayAdoptionProfiles();
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

    private ArrayList<Profile> getAllAdoptionProfiles() {
        ArrayList<Profile> allProfiles = new ArrayList<>();
        ArrayList<RescueCenter> rescueCenters = pawCare.getRescueCenters();

        for (RescueCenter rescueCenter : rescueCenters) {
            ArrayList<Profile> profiles = rescueCenter.getAdoptionProfiles();
            allProfiles.addAll(profiles);
        }

        return allProfiles;
    }

    private void displayNoProfilesMessage() {
        Label noProfilesLabel = new Label("No animal profiles available.");
        noProfilesLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #D08122;");
        profileContainer.getChildren().add(noProfilesLabel);
    }

    private void displayAdoptionProfiles() {
        profileContainer.getChildren().clear();

        if (adoptionProfiles == null || adoptionProfiles.isEmpty()) {
            displayNoProfilesMessage();
            return;
        }

        for (Profile adoptionProf : adoptionProfiles) {
            AnchorPane animalProfilePane = createAdoptionProfilePane(adoptionProf);
            profileContainer.getChildren().add(animalProfilePane);
        }

        mainContainer.setPrefHeight(profileContainer.getChildren().size() * 287);
    }

    private AnchorPane createAdoptionProfilePane(Profile animalProf) {
        Animal animal = animalProf.getAnimal();

        AnchorPane profilePane = new AnchorPane();
        profilePane.setPrefSize(674, 287);  // Set preferred size
        profilePane.setStyle("-fx-background-color: #ffffff; -fx-border-color: #D08122; -fx-border-width: 1; -fx-border-radius: 10;");


        ImageView img = new ImageView();
        img.setFitHeight(180);
        img.setFitWidth(200);
        img.setLayoutX(20);
        img.setLayoutY(40);
        img.setImage(animal.getImage());
        img.setPreserveRatio(true);

        Label nameLabel = createLabel(animal.getName(), 240, 40, "-fx-font-size: 24px; -fx-font-weight: bold;");
        Label typeLabel = createLabel("Type: " + animal.getType(), 240, 70, "");
        Label breedLabel = createLabel("Breed: " + animal.getBreed(), 240, 100, "");
        Label colorLabel = createLabel("Color: " + animal.getColor(), 240, 130, "");

        Label statusLabel = createLabel("Request Status: " + getRequestStatus(loginCredentials.getUsername(),animalProf), 240, 160, "");

        Button applyButton = new Button("Apply for Adoption");
        applyButton.setLayoutX(240);
        applyButton.setLayoutY(200);
        applyButton.setStyle("-fx-background-color: #D08122;");
        applyButton.setOnAction(event -> applyForAdoption(animalProf));

        profilePane.getChildren().addAll(img, nameLabel, typeLabel, breedLabel, colorLabel, statusLabel, applyButton);

        return profilePane;
    }

    private Label createLabel(String text, double x, double y, String style) {
        Label label = new Label(text);
        label.setLayoutX(x);
        label.setLayoutY(y);
        label.setStyle(style);
        return label;
    }

    private void applyForAdoption(Profile animalProf) {

        Animal animal = animalProf.getAnimal();
        String animalId = animal.getAnimalID();
        String rescueCenterId = animalProf.getRescueCenterId();
        String username = loginCredentials.getUsername();
        String userId = getUserFromUsername(username);

        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Adoption Details");

        // Creating the dialog layout
        VBox vbox = new VBox(10);
        CheckBox allergyCheckBox = new CheckBox("Does anyone in your family have allergies to animals?");
        CheckBox livingConditionCheckBox = new CheckBox("Do you have suitable living conditions for this animal?");
        TextField reasonField = new TextField();
        reasonField.setPromptText("Reason for adopting this animal");

        vbox.getChildren().addAll(allergyCheckBox, livingConditionCheckBox, reasonField);
        dialog.getDialogPane().setContent(vbox);

        // Set the OK button's action
        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL, okButtonType);

        // Handle the OK button press (submit the adoption request)
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                // Collect user input
                String allergies = allergyCheckBox.isSelected() ? "Yes" : "No";
                String suitableLivingConditions = livingConditionCheckBox.isSelected() ? "Yes" : "No";
                String reason = reasonField.getText();

                // Create and store the adoption request
                AdoptionRequest adoptionRequest = new AdoptionRequest(" ", userId, rescueCenterId, animalId, allergyCheckBox.isSelected(), livingConditionCheckBox.isSelected(), reason, false,false);
                pawCare.storeAdoptionRequest(adoptionRequest);

                // Display success message
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Adoption Application");
                alert.setHeaderText(null);
                alert.setContentText("You have successfully applied for adoption of " + animalProf.getAnimal().getName() + "!");
                alert.showAndWait();

                // Close the dialog
                dialog.close();
            }
            return null;
        });

        // Show the dialog
        dialog.show();
    }

    private String getUserFromUsername(String username) {
        for (User user : pawCare.getUsers()) {
            if (user.getUserName().equals(username)) {
                return user.getUserID();
            }
        }
        return null;
    }
    private String getRequestStatus(String username,Profile animalProf) {
        AdoptionRequest request = pawCare.getAdoptionRequestByProfile(username,animalProf);

        if (request == null) {
            return "--";
        }

        if (request.getIsResolved()) {
            if (request.isApplicationStatus()) {
                return "Accepted";
            } else {
                return "Rejected";
            }
        }

        return "--";
    }
}
