package com.example.project1;

import com.example.project1.BLL.PawCare;
import com.example.project1.BLL.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VolunteerController extends UserMenuController {

    private static final Logger LOGGER = Logger.getLogger(com.example.project1.VolunteerController.class.getName()); ;
    @FXML
    private TextField RegField;
    @FXML
    private TextField cnicField;
    @FXML
    private TextField modelField;
    @FXML
    private VBox vehicleTypeVBox;
    @FXML
    private Label selectedVehicleLabel;
    @FXML
    private Button submitButton;
    @FXML
    private ImageView imageView;
    @FXML
    private Button btnUploadImage;
    // Variable to store the selected vehicle type
    private String selectedVehicleType;
    private PawCare pawCare;
    private Image image;

    @FXML
    public void initialize() {
        super.initialize();
        pawCare = new PawCare();
        // Set up vehicle type selection
        for (String type : new String[]{"Car", "Motorbike", "Bicycle", "Truck", "Van"}) {
            Text typeText = new Text(type);
            typeText.setStyle("-fx-font-size: 16px; -fx-text-fill: white; -fx-cursor: hand;");
            typeText.setOnMouseClicked(this::handleVehicleTypeClick);
            vehicleTypeVBox.getChildren().add(typeText);
        }
        btnUploadImage.setOnAction(event -> onUploadImage());

        // Set submit button action
        submitButton.setOnAction(event -> handleSubmitAction());
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
                 image = new Image(new FileInputStream(file));

                imageView.setImage(image);
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Failed to load image", e);

            }
        }
    }

    @FXML
    private void handleVehicleTypeClick(MouseEvent event) {
        // Get the clicked vehicle type
        Text clickedText = (Text) event.getSource();
        selectedVehicleType = clickedText.getText();
        selectedVehicleLabel.setText("Selected Vehicle Type: " + selectedVehicleType);
        System.out.println("Vehicle type selected: " + selectedVehicleType);
    }

    @FXML
    private void handleSubmitAction() {
        // Get input values
        String licenseRegistrationNumber = RegField.getText();
        String cnic = cnicField.getText();
        String model = modelField.getText();

        // Validation
        if (licenseRegistrationNumber.isEmpty() || cnic.isEmpty() || selectedVehicleType == null || model.isEmpty()) {
            System.out.println("All fields are required.");
            return;
        }

        // Log or process the information
        System.out.println("Submitted details:");
        System.out.println("License Registration Number: " + licenseRegistrationNumber);
        System.out.println("CNIC: " + cnic);
        System.out.println("Vehicle Type: " + selectedVehicleType);
        System.out.println("Model: " + model);
        pawCare.create_volunteer(Session.getInstance().getLoggedInUser().getUserID(),
                cnic,selectedVehicleType,image,model);

        RegField.clear();
        cnicField.clear();
        modelField.clear();
        selectedVehicleLabel.setText("");
        selectedVehicleType = null;
    }
}
