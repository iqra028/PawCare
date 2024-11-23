package com.example.project1;

import com.example.project1.BLL.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert;

import java.io.IOException;

public class EditAnimalController extends RescueCenterMenuController implements RequiresSharedData {

    private PawCare pawCare;
    private LoginClassCredentials loginCredentials;
    @FXML
    private Label animalIDField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField typeField;
    @FXML
    private TextField breedField;
    @FXML
    private TextField colorField;
    @FXML
    private CheckBox healthStatusCheckBox;
    @FXML
    private CheckBox visitedVetCheckBox;
    @FXML
    private CheckBox withVetCheckBox;
    @FXML
    private CheckBox upForAdoptionCheckBox;
    @FXML
    private CheckBox adoptedCheckBox;
    @FXML
    private TextField temperatureField;
    @FXML
    private TextField heartRateField;
    @FXML
    private TextField respiratoryRateField;
    @FXML
    private TextField capillaryRefillField;
    @FXML
    private TextField bloodOxygenField;
    @FXML
    private TextField bloodGlucoseField;
    @FXML
    private TextField weightField;
    @FXML
    private Button updateProfileButton;

    @FXML
    public void initialize() {
        super.initialize();
    }
    public void start()
    {
        Profile selectedProfile = SharedProfile.getInstance().getSelectedAnimalProfile();

        if (selectedProfile != null) {
            populateFields(selectedProfile);
        }
        updateProfileButton.setOnAction(event ->save());
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
    private void populateFields(Profile selectedProfile) {
        nameField.setText(selectedProfile.getAnimal().getName());
        typeField.setText(selectedProfile.getAnimal().getType());
        breedField.setText(selectedProfile.getAnimal().getBreed());
        colorField.setText(selectedProfile.getAnimal().getColor());

        healthStatusCheckBox.setSelected(selectedProfile.getAnimal().isHealthStatus());
        visitedVetCheckBox.setSelected(selectedProfile.getAnimal().isVisitedVet());
        withVetCheckBox.setSelected(selectedProfile.getAnimal().isWithVet());
        upForAdoptionCheckBox.setSelected(selectedProfile.getAnimal().isUpForAdoption());
        adoptedCheckBox.setSelected(selectedProfile.getAnimal().isAdopted());

        temperatureField.setText(String.valueOf(selectedProfile.getAnimal().getHealth().getTemperature()));
        heartRateField.setText(String.valueOf(selectedProfile.getAnimal().getHealth().getHeartRate()));
        respiratoryRateField.setText(String.valueOf(selectedProfile.getAnimal().getHealth().getRespiratoryRate()));
        capillaryRefillField.setText(String.valueOf(selectedProfile.getAnimal().getHealth().getCapillaryRefillTime()));
        bloodOxygenField.setText(String.valueOf(selectedProfile.getAnimal().getHealth().getBloodOxygenLevel()));
        bloodGlucoseField.setText(String.valueOf(selectedProfile.getAnimal().getHealth().getBloodGlucoseLevel()));
        weightField.setText(String.valueOf(selectedProfile.getAnimal().getHealth().getWeight()));

        animalIDField.setText(selectedProfile.getAnimal().getAnimalID());
    }
    private void save()
    {
        Profile selectedProfile = SharedProfile.getInstance().getSelectedAnimalProfile();
        if (selectedProfile != null) {
            Animal updatedAnimal = selectedProfile.getAnimal();

            updatedAnimal.setName(nameField.getText());
            updatedAnimal.setType(typeField.getText());
            updatedAnimal.setBreed(breedField.getText());
            updatedAnimal.setColor(colorField.getText());
            updatedAnimal.setHealthStatus(healthStatusCheckBox.isSelected());
            updatedAnimal.setVisitedVet(visitedVetCheckBox.isSelected());
            updatedAnimal.setWithVet(withVetCheckBox.isSelected());
            updatedAnimal.setUpForAdoption(upForAdoptionCheckBox.isSelected());
            updatedAnimal.setAdopted(adoptedCheckBox.isSelected());


            updatedAnimal.getHealth().setTemperature(Double.parseDouble(temperatureField.getText()));
            updatedAnimal.getHealth().setHeartRate(Integer.parseInt(heartRateField.getText()));
            updatedAnimal.getHealth().setRespiratoryRate(Integer.parseInt(respiratoryRateField.getText()));
            updatedAnimal.getHealth().setCapillaryRefillTime(Integer.parseInt(capillaryRefillField.getText()));
            updatedAnimal.getHealth().setBloodOxygenLevel(Integer.parseInt(bloodOxygenField.getText()));
            updatedAnimal.getHealth().setBloodGlucoseLevel(Integer.parseInt(bloodGlucoseField.getText()));
            updatedAnimal.getHealth().setWeight(Double.parseDouble(weightField.getText()));


            boolean success = pawCare.updateAnimalProfile(updatedAnimal);
            if (success) {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Profile updated successfully!");
                alert.showAndWait();
                try {
                    HelloApplication.getInstance().changeScene("AnimalProfileAvailableAnimals.fxml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);  // You can set a header text, if necessary
                alert.setContentText("Error updating profile.");
                alert.showAndWait();
            }
        }
    }

}
