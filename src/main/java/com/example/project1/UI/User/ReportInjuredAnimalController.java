package com.example.project1.UI.User;

import com.example.project1.BLL.LoginClassCredentials;
import com.example.project1.BLL.PawCare;
import com.example.project1.BLL.RequiresSharedData;
import com.example.project1.BLL.Location.SharedData;
import com.example.project1.HelloApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.IOException;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
public class ReportInjuredAnimalController extends UserMenuController implements RequiresSharedData {
        private PawCare pawCare;
        private LoginClassCredentials loginCredentials;
        @FXML
        private Button btnSubmit;
        @FXML
        private TextField AnimalType;
        @FXML
        private TextField Breed;
        @FXML
        private TextField InjuryDesc;
        @FXML
        private WebView webView;
        @FXML
        private Button btnFirstAid;


        private static final Logger LOGGER = Logger.getLogger(ReportInjuredAnimalController.class.getName());
        @FXML
        private ImageView imageView;
        @FXML
        private Button btnUploadImage;
        private double latitude;
        private double longitude;
        @FXML
        public void initialize() {
            super.initialize();
            btnSubmit.setOnAction(event -> onSubmitClick());
            btnUploadImage.setOnAction(event -> onUploadImage());
            btnFirstAid.setOnAction(event -> openFirstAid() );

        }
        public void openFirstAid()
        {
            try {
                HelloApplication.getInstance().changeScene("FirstAid.fxml");
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Failed to change scene to Select-UserType.fxml", e);

            }
        }
        public void customInitialize() {
            if (pawCare != null) {
                start(); // Call your start method here if shared data is set
            }
        }
        @Override
        public void setSharedData(PawCare pawCare, LoginClassCredentials loginCredentials) {
            this.pawCare = pawCare;
            this.loginCredentials = loginCredentials;
            customInitialize();
        }
        public void start(){double[] location = pawCare.getLocation();
            latitude = location[0];
            longitude = location[1];
            String mapHtml = pawCare.generatemap();

            WebEngine webEngine = webView.getEngine();
            webEngine.setJavaScriptEnabled(true);
            webEngine.loadContent(mapHtml);
        }

        private void onUploadImage() {
            // Create a FileChooser to let the user pick an image file
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));

            // Open file chooser window
            Stage stage = (Stage) btnUploadImage.getScene().getWindow();
            File file = fileChooser.showOpenDialog(stage);

            if (file != null) {
                try {
                    // Load the image and display it in the ImageView
                    Image image = new Image(new FileInputStream(file));
                    SharedData.getInstance().setImage(image);
                    imageView.setImage(image);
                } catch (IOException e) {
                    LOGGER.log(Level.SEVERE, "Failed to load image", e);
                    showAlert("Error", "Image Upload Failed", "There was an error loading the selected image.");
                }
            }
        }

        private void onSubmitClick() {
            String animalType = AnimalType.getText();
            String breed = Breed.getText();
            String InjuryDescription = InjuryDesc.getText();
            SharedData.getInstance().setAnimalType(animalType);
            SharedData.getInstance().setBreed(breed);
            SharedData.getInstance().setInjuryDesc(InjuryDescription);

            System.out.println(longitude+","+latitude);
            SharedData.getInstance().setLocation(pawCare.getGeoLocation().getLatitude(),pawCare.getGeoLocation().getLongitude());
            SharedData.getInstance().show();


            if (animalType.isEmpty() || InjuryDescription.isEmpty() || breed.isEmpty()) {
                showAlert("Error", "Missing Fields", "Please fill out all fields before submitting.");
            } else {
                List<String> shelterInfoList = pawCare.generatenearbycenters();
                SharedData.getInstance().setRescueCenters(shelterInfoList);
                NextPage();
            }
        }

        private void showAlert(String title, String header, String content) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(header);
            alert.setContentText(content);
            alert.showAndWait();
        }

        private void NextPage() {
            try {
                HelloApplication.getInstance().changeScene("NearbyRescueCenters.fxml");
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Failed to change scene to NearbyRescueCenters.fxml", e);
            }
        }
}