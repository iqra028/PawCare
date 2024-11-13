package com.example.project1;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class HomepageController {

    @FXML
    private ImageView petImageView;
    @FXML
    private ImageView facebookIcon;
    @FXML
    private ImageView pinterestIcon;
    @FXML
    private ImageView instagramIcon;

    @FXML
    private Label genghizKhanLabel;

    @FXML
    private Button reportInjuredAnimalButton;
    @FXML
    private Button missingAnimalButton;
    @FXML
    private Button adoptButton;
    @FXML
    private Button volunteerButton;
    @FXML
    private Button donateButton;

    @FXML
    public void initialize() {
        // Load images
        petImageView.setImage(new Image(getClass().getResourceAsStream("/images/dog_and_cat.jpg")));
        facebookIcon.setImage(new Image(getClass().getResourceAsStream("/images/facebook_icon.png")));
        pinterestIcon.setImage(new Image(getClass().getResourceAsStream("/images/pinterest_icon.png")));
        instagramIcon.setImage(new Image(getClass().getResourceAsStream("/images/instagram_icon.png")));

        // Set up event handlers if necessary
        reportInjuredAnimalButton.setOnAction(event -> handleReportInjuredAnimal());
        missingAnimalButton.setOnAction(event -> handleMissingAnimal());
        adoptButton.setOnAction(event -> handleAdopt());
        volunteerButton.setOnAction(event -> handleVolunteer());
        donateButton.setOnAction(event -> handleDonate());
    }

    private void handleReportInjuredAnimal() {
        // Add code to handle "Report Injured Animal" action
        System.out.println("Report Injured Animal button clicked");
    }

    private void handleMissingAnimal() {
        // Add code to handle "Missing Animal" action
        System.out.println("Missing Animal button clicked");
    }

    private void handleAdopt() {
        // Add code to handle "Adopt" action
        System.out.println("Adopt button clicked");
    }

    private void handleVolunteer() {
        // Add code to handle "Volunteer" action
        System.out.println("Volunteer button clicked");
    }

    private void handleDonate() {
        // Add code to handle "Donate" action
        System.out.println("Donate button clicked");
    }
}
