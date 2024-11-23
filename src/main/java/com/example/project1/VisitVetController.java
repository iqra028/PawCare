package com.example.project1;

import com.example.project1.BLL.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.util.List;

public class VisitVetController extends RescueCenterMenuController implements RequiresSharedData {
    private PawCare pawCare;
    private LoginClassCredentials loginCredentials;

    @FXML
    private ComboBox<String> vetComboBox;

    @FXML
    private Label PawCare; // Label for PawCare title
    @FXML
    private Button confirmButton;

    private String selectedAnimal;

    public void initialize() {
        super.initialize();

    }

    public void start() {
        loadVets();

        Profile animal = SharedProfile.getInstance().getSelectedAnimalProfile();

        if (animal == null) {
            System.out.println("No selected animal profile.");
            return;
        }
        final String[] selectedVet = {""};
        vetComboBox.setOnAction(event -> {
            selectedVet[0] = vetComboBox.getSelectionModel().getSelectedItem();
        });
        if (!vetComboBox.getItems().isEmpty()) {
            selectedVet[0] = vetComboBox.getItems().get(0);
        }
        confirmButton.setOnAction(event -> handleConfirmButtonClick());
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

    private void loadVets() {
        List<Vets> vets = pawCare.getVets();
        for (Vets vet : vets) {
            vetComboBox.getItems().add(vet.getUserName());
        }
        if (!vets.isEmpty()) {
            vetComboBox.getSelectionModel().selectFirst();
        }
    }
    private void sendAnimalToVet(Profile animal, String vetName) {
        try {
            pawCare.sendAnimalToVet(animal, vetName);
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Animal sent to vet");
            alert.setContentText("The animal " + animal + " has been successfully sent to " + vetName + ".");
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to send animal");
            alert.setContentText("There was an error sending the animal to the vet. Please try again.");
            alert.showAndWait();
        }
    }
    private void handleConfirmButtonClick() {
        Profile animal = SharedProfile.getInstance().getSelectedAnimalProfile();
        if (animal == null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No animal selected");
            alert.setContentText("Please select an animal before confirming.");
            alert.showAndWait();
            return;
        }
        String selectedVet = vetComboBox.getSelectionModel().getSelectedItem();
        if (selectedVet == null || selectedVet.isEmpty()) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No vet selected");
            alert.setContentText("Please select a vet before confirming.");
            alert.showAndWait();
            return;
        }

        sendAnimalToVet(animal, selectedVet);
    }


}
