package com.example.project1;

import com.example.project1.BLL.LoginClassCredentials;
import com.example.project1.BLL.PawCare;
import com.example.project1.BLL.RequiresSharedData;
import com.example.project1.BLL.RescueCenter;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class DonateEasypaisaController extends UserMenuController implements RequiresSharedData {

    @FXML
    private TextField phoneNumber;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField amount;
    private LoginClassCredentials loginCredentials;
    @FXML
    private Button submitbutton;
    @FXML
    private Button Missing; // By Card button
    @FXML
    private ComboBox<String> rescueCenterDropdown;
    @FXML
    private Button Found;
    private PawCare pawCare; // Reference to PawCare instance

    @Override
    public void initialize() {
        super.initialize();

    }
    private void openCardPage() {
        try {
            HelloApplication.getInstance().changeScene("DonateForm.fxml");        } catch (IOException e) {
            e.printStackTrace();
        }
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
    void start(){
        populateRescueCenters();
        Missing.setOnAction(event -> openCardPage());
        Found.setOnAction(event -> openEasypaisaPage());
        submitbutton.setOnAction(event -> handleSubmit());
    }

    private void openEasypaisaPage() {

        try {
            HelloApplication.getInstance().changeScene("DonateEasypaisa.fxml");            } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void populateRescueCenters() {
        // Assuming PawCare has a method to fetch rescue center names
        ArrayList<RescueCenter> rescueCenters = pawCare.getRescueCenters();
        List<String> rescueCenterNames = new ArrayList<>();
        for (RescueCenter rescueCenter : rescueCenters) {
            rescueCenterNames.add(rescueCenter.getName());
        }

        // Convert list to ObservableList and populate ComboBox
        ObservableList<String> centerOptions = FXCollections.observableArrayList(rescueCenterNames);
        rescueCenterDropdown.setItems(centerOptions);
    }

    @FXML
    void handleSubmit() {
        if (!validateInputs()) {
            System.out.println("Please fill in all required fields.");
            return;
        }

        String phone = phoneNumber.getText();
        String firstname = firstName.getText();
        String lastname = lastName.getText();
        String donationAmount = amount.getText();
        String selectedRescueCenter = rescueCenterDropdown.getValue();

        if (selectedRescueCenter == null) {
            System.out.println("Please select a rescue center.");
            return;
        }
        pawCare.processDonation(phone,firstname,lastname,donationAmount,pawCare.getRescueCenterIDByName(selectedRescueCenter));



    }
    private boolean validateInputs() {

        return !phoneNumber.getText().isEmpty() && !firstName.getText().isEmpty() &&
                !lastName.getText().isEmpty() &&
                !amount.getText().isEmpty();
    }

}
